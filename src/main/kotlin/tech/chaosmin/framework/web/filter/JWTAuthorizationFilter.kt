package tech.chaosmin.framework.web.filter

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.utils.HttpUtil
import tech.chaosmin.framework.utils.JwtTokenUtil
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 用户登录鉴权处理器
 *
 * @author Romani min
 * @since 2020/11/23 18:14
 */
class JWTAuthorizationFilter(authManager: AuthenticationManager) :
    BasicAuthenticationFilter(authManager, JWTAuthenticationEntryPoint()) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER)
        if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            chain.doFilter(request, response)
            return
        }
        SecurityContextHolder.getContext().authentication = JwtTokenUtil.getAuthenticationFromToken(tokenHeader)
        super.doFilterInternal(request, response, chain)
    }
}

class JWTAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        HttpUtil.write(response, RestResultExt.badCredentialsRestResult(authException.message ?: ""))
    }
}