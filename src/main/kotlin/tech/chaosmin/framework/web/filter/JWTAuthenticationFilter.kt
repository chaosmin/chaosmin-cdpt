package tech.chaosmin.framework.web.filter

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.definition.SystemConst.DEFAULT_CHARSET_NAME
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.mgmt.domain.auth.JwtAuthenticationToken
import tech.chaosmin.framework.module.mgmt.domain.auth.LoginParameter
import tech.chaosmin.framework.utils.HttpUtil
import tech.chaosmin.framework.utils.JsonUtil
import tech.chaosmin.framework.utils.JwtTokenUtil
import tech.chaosmin.framework.utils.SecurityUtil
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 用户登录验证处理器
 *
 * @author romani min
 */
class JWTAuthenticationFilter(authManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {
    init {
        authenticationManager = authManager
        super.setFilterProcessesUrl("/auth/login")
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        // 可以在此覆写尝试进行登录认证的逻辑，登录成功之后等操作不再此方法内
        // 如果使用此过滤器来触发登录认证流程，注意登录请求数据格式的问题
        // 此过滤器的用户名密码默认从request.getParameter()获取，但是这种
        // 读取方式不能读取到如 application/json 等 post 请求数据，需要把
        // 用户名密码的读取逻辑修改为到流中读取request.getInputStream()
        val loginParameter = JsonUtil.decode(getBody(request), LoginParameter::class.java)
        val authRequest = JwtAuthenticationToken(loginParameter?.loginName, loginParameter?.password)
        return authenticationManager.authenticate(authRequest)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest, response: HttpServletResponse,
        chain: FilterChain, authResult: Authentication
    ) {
        // 存储登录认证信息到上下文
        SecurityUtil.setAuthentication(authResult)
        // 记住我服务
        rememberMeServices.loginSuccess(request, response, authResult)
        // 触发事件监听器
        if (eventPublisher != null) {
            eventPublisher.publishEvent(InteractiveAuthenticationSuccessEvent(authResult, this.javaClass))
        }
        // 生成并返回token给客户端，后续访问携带此token
        val generateToken = JwtTokenUtil.generateToken(authResult)
        val username = SecurityUtil.getUsername(authResult)
        if (username.isBlank()) {
            throw FrameworkException(ErrorCodeEnum.USER_NOT_FOUND.code)
        }
        // 清除一下用户的菜单列表缓存
        // SpringUtil.getBean(StoreService::class.java).clear(username)
        // val authorityService = SpringUtil.getBean(AuthorityService::class.java)
        // val authorities = authResult.authorities.mapNotNull { authorityService.findAuthorities(it.authority) }
        // val authentication = JwtAuthenticationToken(username, authorities = authorities, token = generateToken)
        val authentication = JwtAuthenticationToken(username, token = generateToken)
        response.setHeader(JwtTokenUtil.TOKEN_HEADER, "${JwtTokenUtil.TOKEN_PREFIX}$generateToken")
        HttpUtil.write(response, RestResultExt.successRestResult(authentication))
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        HttpUtil.write(response, RestResultExt.badCredentialsRestResult())
    }

    /**
     * 获取请求Body
     * @param request
     * @return
     */
    private fun getBody(request: HttpServletRequest): String {
        request.inputStream.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream, Charset.forName(DEFAULT_CHARSET_NAME))).use { reader ->
                return reader.lines().collect(Collectors.joining(""))
            }
        }
    }
}