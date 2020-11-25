package tech.chaosmin.framework.web.filter

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import tech.chaosmin.framework.domain.auth.AnonymousAuthentication
import tech.chaosmin.framework.utils.JwtTokenUtil
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Romani min
 * @since 2020/11/23 18:14
 */
class JWTAuthorizationFilter(authManager: AuthenticationManager, private val globalAnonymous: Boolean = false) :
    BasicAuthenticationFilter(authManager) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        if (globalAnonymous) {
            SecurityContextHolder.getContext().authentication = AnonymousAuthentication
            return
        }
        val tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER)
        if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            chain.doFilter(request, response)
            return
        }
        SecurityContextHolder.getContext().authentication = JwtTokenUtil.getAuthenticationFromToken(tokenHeader)
        super.doFilterInternal(request, response, chain)
    }
}