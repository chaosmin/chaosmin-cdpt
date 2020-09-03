package tech.chaosmin.framework.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import tech.chaosmin.framework.domain.auth.GrantedAuthorityImpl
import tech.chaosmin.framework.domain.auth.JwtAuthenticationToken
import java.io.Serializable
import java.util.*
import javax.servlet.http.HttpServletRequest


object JwtTokenUtil : Serializable {
    private const val USERNAME: String = Claims.SUBJECT
    private const val CREATED = "created"
    private const val AUTHORITIES = "authorities"
    private const val SECRET = "abcdefgh"
    private const val EXPIRE_TIME = (12 * 60 * 60 * 1000).toLong()

    /**
     * 生成令牌
     *
     * @param authentication 用户
     * @return 令牌
     */
    fun generateToken(authentication: Authentication): String? {
        val claims: MutableMap<String, Any?> = HashMap(3)
        claims[USERNAME] = SecurityUtil.getUsername(authentication)
        claims[CREATED] = Date()
        claims[AUTHORITIES] = authentication.authorities
        return generateToken(claims)
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private fun generateToken(claims: Map<String, Any?>): String? {
        val expirationDate = Date(System.currentTimeMillis() + EXPIRE_TIME)
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, SECRET)
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
            getClaimsFromToken(token)?.subject
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 根据请求令牌获取登录认证信息
     *
     * @param token 令牌
     * @return 用户名
     */
    fun getAuthenticationFromToken(request: HttpServletRequest): Authentication? {
        var authentication: Authentication? = null
        // 获取请求携带的令牌
        val token = getToken(request)
        if (token != null) {
            // 请求令牌不能为空
            if (SecurityUtil.getAuthentication() == null) {
                // 上下文中Authentication为空
                val claims = getClaimsFromToken(token) ?: return null
                val username = claims.subject ?: return null
                if (isTokenExpired(token)) {
                    return null
                }
                val authors = claims[AUTHORITIES]
                val authorities = mutableListOf<GrantedAuthority>()
                if (authors != null && authors is List<*>) {
                    for (obj in authors) {
                        val authority = JsonUtil.encode((obj as Map<*, *>)["authority"])
                        authorities.add(GrantedAuthorityImpl(authority))
                    }
                }
                authentication = JwtAuthenticationToken(username, null, authorities, token)
            } else {
                if (validateToken(token, SecurityUtil.getUsername())
                ) {
                    // 如果上下文中Authentication非空，且请求令牌合法，直接返回当前登录认证信息
                    authentication = SecurityUtil.getAuthentication()
                }
            }
        }
        return authentication
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private fun getClaimsFromToken(token: String): Claims? {
        return try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).body
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
     * 刷新令牌
     *
     * @param token 令牌
     * @return
     */
    fun refreshToken(token: String): String? {
        return try {
            val claims = getClaimsFromToken(token)
            if (claims != null) {
                claims[CREATED] = Date()
                generateToken(claims)
            } else null
        } catch (e: java.lang.Exception) {
            null
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
        } catch (e: java.lang.Exception) {
            false
        }
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return
     */
    private fun getToken(request: HttpServletRequest): String? {
        var token = request.getHeader("Authorization") ?: request.getHeader("token")
        val tokenHead = "Bearer "
        if (token.contains(tokenHead)) {
            token = token.substring(tokenHead.length)
        }
        if ("" == token) {
            token = null
        }
        return token
    }
}