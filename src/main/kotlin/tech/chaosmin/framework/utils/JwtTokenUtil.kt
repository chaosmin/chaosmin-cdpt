package tech.chaosmin.framework.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.AuthenticationException
import tech.chaosmin.framework.module.mgmt.domain.auth.GrantedAuthorityImpl
import tech.chaosmin.framework.module.mgmt.domain.auth.JwtAuthenticationToken
import tech.chaosmin.framework.module.mgmt.domain.auth.JwtUserDetails
import java.io.Serializable
import java.util.*


object JwtTokenUtil : Serializable {
    private val logger = LoggerFactory.getLogger(JwtTokenUtil::class.java)

    const val TOKEN_HEADER = "Authorization"
    const val TOKEN_PREFIX = "Bearer "

    private const val ISS = "chaosmin"
    private const val APP_SECRET_KEY = "chaosmin_secret"
    private const val DEPARTMENT = "department"
    private const val USERID = "userId"
    private const val USERNAME = "username"
    private const val ROLES = "roles"
    private const val AUTHORITIES = "authorities"
    private const val CREATED = "created"

    // The normal expire time is 12 hours
    private const val EXPIRE_TIME = (12 * 60 * 60 * 1000).toLong()

    // The expire time for remember me is 7 days
    private const val EXPIRE_TIME_REMEMBER = (7 * 2 * EXPIRE_TIME)

    /**
     * 生成令牌
     *
     * @param authentication 用户
     * @return 令牌
     */
    fun generateToken(authentication: Authentication, isRememberMe: Boolean = false): String? {
        val claims: MutableMap<String, Any?> = HashMap(8)
        val userDetails = SecurityUtil.getUserDetails(authentication)
        claims[USERID] = userDetails?.userId
        claims[USERNAME] = userDetails?.userName
        claims[DEPARTMENT] = userDetails?.departmentId
        claims[ROLES] = userDetails?.roles
        claims[AUTHORITIES] = authentication.authorities
        claims[CREATED] = Date()
        return generateToken(userDetails?.userName, claims, isRememberMe)
    }

    /**
     * 刷新令牌
     *
     * @param token 令牌
     * @return
     */
    fun refreshToken(token: String): String? {
        return try {
            val claims = getClaimsFromToken(token)
            if (claims != null) {
                val username = claims.subject
                claims[CREATED] = Date()
                generateToken(username, claims, false)
            } else null
        } catch (e: java.lang.Exception) {
            null
        }
    }

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @param username 用户名
     * @return
     */
    private fun validateToken(token: String, username: String?): Boolean {
        val userName = getUsernameFromToken(token)
        return userName == username && !isTokenExpired(token)
    }

    /**
     * 根据请求令牌获取登录认证信息
     *
     * @param tokenHeader 请求TOKEN
     * @return 用户信息
     */
    fun getAuthenticationFromToken(tokenHeader: String): Authentication? {
        var authentication: Authentication? = null
        // 获取请求携带的令牌
        getToken(tokenHeader)?.run {
            // 请求令牌不能为空
            if (SecurityUtil.getAuthentication() == null) {
                // 上下文中Authentication为空
                val claims = getClaimsFromToken(this) ?: return null
                val userId = claims[USERID]?.toString() ?.toLong()
                val username = claims[USERNAME]?.toString() ?: return null
                val department = claims[DEPARTMENT]?.toString()?.toLong()
                @Suppress("UNCHECKED_CAST") val roles = claims[ROLES] as List<String>
                if (isTokenExpired(this)) {
                    return null
                }
                authentication = JwtAuthenticationToken(
                    JwtUserDetails(userId!!, username, "", department, roles),
                    authorities = generateAuthorities(claims[AUTHORITIES]),
                    token = this
                )
                SecurityUtil.setAuthentication(authentication!!)
            } else {
                if (validateToken(this, SecurityUtil.getUsername())) {
                    // 如果上下文中Authentication非空，且请求令牌合法，直接返回当前登录认证信息
                    authentication = SecurityUtil.getAuthentication()
                }
            }
        }
        return authentication
    }

    /**
     * 从claims数据中恢复用户的菜单权限
     *
     * @param authors claims信息数据
     * @return 用户菜单列表
     */
    private fun generateAuthorities(authors: Any?): List<GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>()
        if (authors != null && authors is List<*>) {
            for (obj in authors) {
                val authority = JsonUtil.encode((obj as Map<*, *>)["authority"])
                authorities.add(GrantedAuthorityImpl(authority))
            }
        }
        return authorities
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private fun generateToken(username: String?, claims: Map<String, Any?>, isRememberMe: Boolean): String? {
        val expireTime = if (isRememberMe) EXPIRE_TIME_REMEMBER else EXPIRE_TIME
        val expirationDate = Date(System.currentTimeMillis() + expireTime)
        return Jwts.builder()
            .setIssuer(ISS)
            .setSubject(username)
            .setIssuedAt(Date())
            .setClaims(claims)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, APP_SECRET_KEY)
            .compact()
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    private fun getUsernameFromToken(token: String): String? {
        return try {
            getClaimsFromToken(token)?.get(USERNAME)?.toString()
        } catch (e: Exception) {
            throw AuthenticationException(ErrorCodeEnum.TOKEN_INVALID.code, e)
        }
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    private fun isTokenExpired(token: String): Boolean {
        return try {
            val claims = getClaimsFromToken(token)
            if (claims != null) {
                val expiration = claims.expiration
                expiration.before(Date())
            } else false
        } catch (e: Exception) {
            throw AuthenticationException(ErrorCodeEnum.TOKEN_EXPIRED.code, e)
        }.also {
            if (it) logger.warn("$token has been expired")
        }
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private fun getClaimsFromToken(token: String): Claims? {
        return try {
            Jwts.parser().setSigningKey(APP_SECRET_KEY).parseClaimsJws(token).body
        } catch (e: Exception) {
            throw AuthenticationException(ErrorCodeEnum.TOKEN_INVALID.code, e)
        }
    }

    /**
     * 获取请求token
     *
     * @param tokenHeader 形式为'Bearer xxxxx'的token
     * @return
     */
    private fun getToken(tokenHeader: String): String? {
        var token: String? = tokenHeader
        if (tokenHeader.contains(TOKEN_PREFIX)) {
            token = tokenHeader.substring(TOKEN_PREFIX.length).trim()
        }
        if ("" == token) {
            token = null
        }
        return token
    }
}