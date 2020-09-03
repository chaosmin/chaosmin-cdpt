package tech.chaosmin.framework.utils

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import tech.chaosmin.framework.domain.auth.JwtAuthenticationToken
import javax.servlet.http.HttpServletRequest

object SecurityUtil {
    fun login(
        request: HttpServletRequest,
        username: String,
        password: String,
        authenticationManager: AuthenticationManager
    ): JwtAuthenticationToken {
        val token = JwtAuthenticationToken(username, password)
        token.details = WebAuthenticationDetailsSource().buildDetails(request)
        // 执行登录认证过程
        val authentication = authenticationManager.authenticate(token)
        // 认证成功存储认证信息到上下文
        SecurityContextHolder.getContext().authentication = authentication
        // 生成令牌并返回给客户端
        token.token = JwtTokenUtil.generateToken(authentication)
        return token
    }

    fun checkAuthentication(request: HttpServletRequest) {
        // 获取令牌并根据令牌获取登录认证信息
        val authentication = JwtTokenUtil.getAuthenticationFromToken(request)
        // 设置登录认证信息到上下文
        SecurityContextHolder.getContext().authentication = authentication
    }

    fun getUsername(): String? {
        var username: String? = null
        val authentication = getAuthentication()
        if (authentication != null) {
            val principal: Any = authentication.principal
            if (principal is UserDetails) {
                username = principal.username
            }
        }
        return username
    }

    fun getUsername(authentication: Authentication?): String? {
        var username: String? = null
        if (authentication != null) {
            val principal: Any = authentication.principal
            if (principal is UserDetails) {
                username = principal.username
            }
        }
        return username
    }

    fun getAuthentication(): Authentication? {
        return if (SecurityContextHolder.getContext() == null) null
        else SecurityContextHolder.getContext().authentication
    }
}