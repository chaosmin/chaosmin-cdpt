package tech.chaosmin.framework.web.interceptor

import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.exception.PermissionException
import tech.chaosmin.framework.module.mgmt.domain.auth.Action
import tech.chaosmin.framework.module.mgmt.domain.auth.AuthContextHolder
import tech.chaosmin.framework.module.mgmt.service.AuthService
import tech.chaosmin.framework.utils.JwtTokenUtil
import tech.chaosmin.framework.utils.SecurityUtil
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(-1000)
class AuthInterceptor(private val authService: AuthService) : HandlerInterceptor {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val uri: String = request.requestURI
        val action = Action(request.method, uri)
        val token =
            request.getHeader(JwtTokenUtil.TOKEN_HEADER).takeUnless { it.isNullOrBlank() } ?: throw FrameworkException(ErrorCodeEnum.NO_TOKEN.code)
        val authentication = JwtTokenUtil.getAuthenticationFromToken(token) ?: throw PermissionException(ErrorCodeEnum.NO_PERMISSION.code)
        if (!authService.canAccess(authentication, action)) {
            logger.warn("${SecurityUtil.getUsername(authentication)} does not have access to $action")
            throw PermissionException(ErrorCodeEnum.NO_PERMISSION.code)
        }
        AuthContextHolder.setAuthentication(authentication)
        return true
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        SecurityUtil.clean()
    }
}