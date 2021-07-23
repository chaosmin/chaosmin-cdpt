package tech.chaosmin.framework.web.filter

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.FrameworkException
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
class JWTAuthorizationFilter(authManager: AuthenticationManager) : BasicAuthenticationFilter(authManager) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            val tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER)
            if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
                HttpUtil.write(response, RestResult<String>(ErrorCodeEnum.NO_TOKEN.code))
                return
            }
            // 填充Token信息
            JwtTokenUtil.getAuthenticationFromToken(tokenHeader)
        } catch (e: FrameworkException) {
            this.logger.error("Login error! code: ${e.errCode}, msg: ${e.message}")
            HttpUtil.write(response, RestResultExt.failureRestResult(e))
            return
        }
        super.doFilterInternal(request, response, chain)
    }
}