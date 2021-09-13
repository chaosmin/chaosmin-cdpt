package tech.chaosmin.framework.configuration

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.multipart.MultipartFile
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.utils.JsonUtil
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import kotlin.reflect.KClass

@Aspect
@Component
@ConditionalOnProperty(prefix = "log.provider", name = ["enable"], havingValue = "true", matchIfMissing = false)
class ProviderLogConfig {
    private val ignoreTypes: Array<KClass<*>> = arrayOf(ServletRequest::class, ServletResponse::class)
    private val logger = LoggerFactory.getLogger(ProviderLogConfig::class.java)

    @Around(
        "execution(public * tech.chaosmin.framework.base.AbstractAPI.*(..))" +
                "|| execution(public * tech.chaosmin.framework.module.*.api.provider.*.*(..))"
    )
    @Throws(Throwable::class)
    fun doAround(joinPoint: ProceedingJoinPoint): Any? {
        // 1 先过滤出有RequestMapping的方法
        val signature = joinPoint.signature as? MethodSignature ?: return joinPoint.proceed()
        val method = signature.method

        val lineSeparator = System.lineSeparator()
        val logContent = StringBuilder(lineSeparator)

        logContent.append(String.format("%-25s: %s.%s", "Provider class", method.declaringClass.name, method.name))
        logContent.append(lineSeparator)

        val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        val request = attributes.request

        logContent.append(String.format("%-25s: %s", "Provider URL", "[${request.method}] ${request.requestURI}" + lineSeparator))

        val params = HashMap<String, Any>()
        val args = joinPoint.args
        val parameterNames = signature.parameterNames
        for (i in args.indices) {
            addRequestParam(args[i], parameterNames[i], params)
        }
        logContent.append(String.format("%-25s: %s", "Provider request param", JsonUtil.encode(params) + lineSeparator))
        val startTime = System.currentTimeMillis()
        return try {
            val r = onProcess(joinPoint)
            logContent.append(String.format("%-25s: %s", "Provider response param", JsonUtil.encode(r) + lineSeparator))
            logContent.append(String.format("%-25s: %sms", "Provider cost time", (System.currentTimeMillis() - startTime)))
            r
        } catch (ex: java.lang.Exception) {
            logContent.append(String.format("%-25s: %s", "Provider Error", ex.message + lineSeparator))
            logContent.append(String.format("%-25s: %sms", "Provider cost time", (System.currentTimeMillis() - startTime)))
            onException(logger, ex)
        } finally {
            logger.info(logContent.toString())
        }
    }

    @Throws(Throwable::class)
    private fun onProcess(joinPoint: ProceedingJoinPoint): Any? {
        return joinPoint.proceed()
    }

    /**
     * 排除需要记录的Java类型
     */
    private fun contains(classes: Array<KClass<*>>, entity: Any?): Boolean {
        if (entity == null) return false
        return classes.any { entity.javaClass.name == it.qualifiedName }
    }

    private fun addRequestParam(param: Any?, paramName: String, params: HashMap<String, Any>) {
        if (param != null) {
            if (param is Array<*>) {
                param.forEachIndexed { i, v -> addRequestParam(v, "${paramName}[$i]", params) }
            } else if (param is MultipartFile) {
                params[paramName] = param.originalFilename ?: ""
            } else if ("request" != paramName && "response" != paramName && !contains(ignoreTypes, param)) {
                params[paramName] = param
            }
        }
    }

    private fun onException(log: Logger, ex: Exception): RestResult<Void> {
        log.error("Provider Error {}", ex, ex)
        return RestResultExt.failureRestResult("System error, please try later again!")
    }
}