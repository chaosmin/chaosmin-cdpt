package tech.chaosmin.framework.web.filter

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.springframework.web.filter.AbstractRequestLoggingFilter
import java.util.*
import javax.servlet.http.HttpServletRequest
import kotlin.math.abs

@Component
@ConditionalOnProperty(prefix = "log.access", name = ["enable"], havingValue = "true", matchIfMissing = false)
class AccessLogFilter : AbstractRequestLoggingFilter() {
    private val log: Logger = LoggerFactory.getLogger(AccessLogFilter::class.java)
    private val random = Random(System.currentTimeMillis())
    private val traceIDKey = "TRACE_ID"

    override fun beforeRequest(request: HttpServletRequest, message: String) {
        val traceId = abs(random.nextLong()).toString(16)
        request.setAttribute(traceIDKey, traceId)
        MDC.put(traceIDKey, traceId)
        val remoteIp = request.getHeader("X-Forwarded-For") ?: request.remoteAddr
        log.info("[{}] {}, from: {}", request.method, request.requestURI, remoteIp)
    }

    override fun afterRequest(request: HttpServletRequest, message: String) {
        request.removeAttribute(traceIDKey)
        MDC.remove(traceIDKey)
    }
}