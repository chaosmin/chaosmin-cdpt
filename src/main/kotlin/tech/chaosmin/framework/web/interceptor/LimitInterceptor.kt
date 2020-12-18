package tech.chaosmin.framework.web.interceptor

import cn.hutool.core.util.EnumUtil
import com.google.common.util.concurrent.RateLimiter
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import tech.chaosmin.framework.domain.const.ServerLimitParam
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.FrameworkException
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Romani min
 * @since 2020/11/30 17:53
 */
@Suppress("UnstableApiUsage")
class LimitInterceptor(serverLimitParam: ServerLimitParam) : HandlerInterceptorAdapter() {
    enum class LimitType { DROP, WAIT }

    private val limiter: RateLimiter =
        RateLimiter.create(serverLimitParam.permitsPerSecond, serverLimitParam.tps, TimeUnit.SECONDS)
    private val limitType: LimitType =
        EnumUtil.fromStringQuietly(LimitType::class.java, serverLimitParam.limitType)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (LimitType.DROP == limitType) {
            if (limiter.tryAcquire()) {
                return super.preHandle(request, response, handler)
            }
        } else {
            limiter.acquire()
            return super.preHandle(request, response, handler)
        }
        throw FrameworkException(ErrorCodeEnum.REQUEST_LIMITED.code)
    }
}