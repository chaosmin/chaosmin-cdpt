package tech.chaosmin.framework.config

import org.apache.commons.lang3.ArrayUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.utils.JsonUtil
import java.util.function.Function

@Aspect
@Component
@ConditionalOnProperty(prefix = "log.provider", name = ["enable"], havingValue = "true", matchIfMissing = false)
class ProviderLogConfig {
    private val log: Logger = LoggerFactory.getLogger(ProviderLogConfig::class.java)

    @Around("execution(public * tech.chaosmin.framework.provider.*.*(..))")
    @Throws(Throwable::class)
    fun doAround(joinPoint: ProceedingJoinPoint): Any? {
        // 1 先过滤出有RequestMapping的方法
        val signature = joinPoint.signature as? MethodSignature ?: return joinPoint.proceed()
        val method = signature.method

        val lineSeparator = System.lineSeparator()
        val logContent = StringBuilder(lineSeparator)

        // 2.2 获取接口的类及方法名
        logContent.append(String.format("Provider class:\t%s.%s", method.declaringClass.name, method.name))
        logContent.append(lineSeparator)

        // 2.3 拼装接口URL
        // 2.3.1 获取Class上的FeignURL
        val parentClazz = method.declaringClass.interfaces[0]
        var url = ""
        val reqMappingFunc = Function { p: RequestMapping ->
            java.lang.String.join(",", *if (ArrayUtils.isNotEmpty<String>(p.value)) p.value else p.path)
        }

        val baseMapping = parentClazz.getAnnotation(RequestMapping::class.java)

        if (baseMapping != null) {
            url += reqMappingFunc.apply(baseMapping)
        }

        val methodParent = parentClazz.getDeclaredMethod(method.name, *method.parameterTypes)

        // 2.3.2 获取Class RequestMapping URL
        val methodReqMapping = methodParent.getAnnotation(RequestMapping::class.java)
        if (methodReqMapping != null) {
            url += reqMappingFunc.apply(methodReqMapping)
        }

        logContent.append(String.format("Provider URL:\t\t%s", url + lineSeparator))
        logContent.append(String.format("Provider request param:\t%s", JsonUtil.encode(joinPoint.args) + lineSeparator))
        return try {
            val r: Any = onProcess(joinPoint)
            logContent.append(java.lang.String.format("Provider response param:\t%s", JsonUtil.encode(r)))
            r
        } catch (ex: java.lang.Exception) {
            logContent.append(String.format("Error:\t\t%s", ex.message))
            onException(log, ex)
        } finally {
            log.info(logContent.toString())
        }
    }

    @Throws(Throwable::class)
    private fun onProcess(joinPoint: ProceedingJoinPoint): Any {
        return joinPoint.proceed()
    }

    private fun onException(log: Logger, ex: Exception): RestResult<Void> {
        log.error("Provider Error {}", ex, ex)
        return RestResultExt.failureRestResult("System error, please try later!")
    }
}