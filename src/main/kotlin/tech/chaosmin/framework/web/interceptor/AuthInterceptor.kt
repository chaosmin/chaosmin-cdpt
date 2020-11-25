package tech.chaosmin.framework.web.interceptor

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import tech.chaosmin.framework.domain.auth.HttpMethodAction
import tech.chaosmin.framework.domain.auth.UrlAction
import tech.chaosmin.framework.domain.configuration.Server
import tech.chaosmin.framework.utils.SecurityUtil
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(-1000)
class AuthInterceptor(private val server: Server) : HandlerInterceptor {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any?): Boolean {
        val uri: String = request.requestURI

        val except = server.interceptor?.except
        if (except?.any { uri.matches((it.replace("**", ".*")).toRegex()) } == true) {
            return true
        }

        val urlAction = UrlAction(uri)
        val httpMethodAction = HttpMethodAction(request.method)

        // Certification
//        val authentication = try {
//            val authentication = authService.authenticateByAnonymous()
//            logger.info(
//                "Anonymous authentication: operator[{}], roles{}",
//                authentication.username,
//                authentication.roles
//            )
//            authentication
//        } catch (ex: AuthenticationException) {
//            val token = request.getHeader(JwtTokenUtil.TOKEN_HEADER).takeUnless { it.isNullOrBlank() }
//                ?: request.getParameter("token").takeUnless { it.isNullOrBlank() }
//                ?: throw AuthenticationException("token is necessary")
//            try {
//                val authentication = authService.authenticateByToken(token)
//                logger.info(
//                    "Token authentication success: operator[{}], roles{}, token[{}]",
//                    authentication.username,
//                    authentication.roles,
//                    token
//                )
//                authentication
//            } catch (ex: AuthenticationException) {
//                logger.error("Token authentication failed: token[{}]", token)
//                throw ex
//            }
//        }
//        AuthContextHolder.setAuthentication(authentication)
//
//        // Authorization
//        if (authService.canAccess(authentication, urlAction)) {
//            logger.info(
//                "Token Authorization success: operator[{}], roles{}, action[{}]: {}",
//                authentication.username,
//                authentication.roles,
//                urlAction.type,
//                urlAction.value
//            )
//        } else {
//            logger.error(
//                "Token Authorization failed: operator[{}], roles{}, action[{}]: {}",
//                authentication.username,
//                authentication.roles,
//                urlAction.type,
//                urlAction.value
//            )
//            throw PermissionException("No url operating rights")
//        }
//        if (authService.canAccess(authentication, httpMethodAction)) {
//            logger.info(
//                "Token Authorization success: operator[{}], roles{}, action[{}]: {}",
//                authentication.username,
//                authentication.roles,
//                httpMethodAction.type,
//                httpMethodAction.value
//            )
//        } else {
//            logger.error(
//                "Token Authorization failed: operator[{}], roles{}, action[{}]: {}",
//                authentication.username,
//                authentication.roles,
//                httpMethodAction.type,
//                httpMethodAction.value
//            )
//            throw PermissionException("No method operating rights")
//        }
        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any?,
        ex: Exception?
    ) {
        SecurityUtil.clean()
    }
}