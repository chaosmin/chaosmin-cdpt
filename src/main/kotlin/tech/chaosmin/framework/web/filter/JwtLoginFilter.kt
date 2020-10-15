package tech.chaosmin.framework.web.filter

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import tech.chaosmin.framework.domain.auth.JwtAuthenticationToken
import tech.chaosmin.framework.utils.HttpUtil
import tech.chaosmin.framework.utils.JwtTokenUtil
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtLoginFilter() : UsernamePasswordAuthenticationFilter() {
    private var skipAuthenticate = false

    constructor(authManager: AuthenticationManager) : this() {
        authenticationManager = authManager
    }

    constructor(authManager: AuthenticationManager, interceptorDebug: Boolean) : this(authManager) {
        this.skipAuthenticate = interceptorDebug
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        if (skipAuthenticate) {
            return authenticationManager.authenticate(JwtAuthenticationToken("admin", "admin"))
        }
        // 可以在此覆写尝试进行登录认证的逻辑，登录成功之后等操作不再此方法内
        // 如果使用此过滤器来触发登录认证流程，注意登录请求数据格式的问题
        // 此过滤器的用户名密码默认从request.getParameter()获取，但是这种
        // 读取方式不能读取到如 application/json 等 post 请求数据，需要把
        // 用户名密码的读取逻辑修改为到流中读取request.getInputStream()
        val body = getBody(request)
        val params = body.split("&")
        val username = params.filter { it.contains("username") }[0].substringAfterLast("=").trim()
        val password = params.filter { it.contains("password") }[0].substringAfterLast("=").trim()
        val authRequest = JwtAuthenticationToken(username, password)
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest)
        return authenticationManager.authenticate(authRequest)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        // 存储登录认证信息到上下文
        SecurityContextHolder.getContext().authentication = authResult
        // 记住我服务
        rememberMeServices.loginSuccess(request, response, authResult)
        // 触发事件监听器
        if (eventPublisher != null) {
            eventPublisher.publishEvent(InteractiveAuthenticationSuccessEvent(authResult, this.javaClass))
        }
        // 生成并返回token给客户端，后续访问携带此token
        val token = JwtAuthenticationToken(null, null, token = JwtTokenUtil.generateToken(authResult))
        HttpUtil.write(response, token)
    }

    /**
     * 获取请求Body
     * @param request
     * @return
     */
    private fun getBody(request: HttpServletRequest): String {
        request.inputStream.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream, Charset.forName("UTF-8"))).use { reader ->
                return reader.lines().collect(Collectors.joining("&"))
            }
        }
    }
}