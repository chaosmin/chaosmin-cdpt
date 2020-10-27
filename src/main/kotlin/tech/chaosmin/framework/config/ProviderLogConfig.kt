package tech.chaosmin.framework.config

import org.apache.catalina.connector.RequestFacade
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.multipart.MultipartFile
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.utils.JsonUtil
import java.util.function.Function
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import kotlin.reflect.KClass

@Aspect
@Component
@ConditionalOnProperty(prefix = "log.provider", name = ["enable"], havingValue = "true", matchIfMissing = false)
class ProviderLogConfig {
    private val ignoreTypes: Array<KClass<*>> = arrayOf(
        RequestFacade::class,
        ServletRequest::class,
        ServletResponse::class
    )
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
        logContent.append(String.format("%-25s: %s.%s", "Provider class", method.declaringClass.name, method.name))
        logContent.append(lineSeparator)

        // 2.3 拼装接口URL
        // 2.3.1 获取Class上的FeignURL
        val parentClazz = method.declaringClass.interfaces[0]
        var url = ""
        val reqMappingFunc = Function { p: RequestMapping ->
            java.lang.String.join(",", *if (p.value.isNotEmpty()) p.value else p.path)
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

        logContent.append(String.format("%-25s: %s", "Provider URL", url + lineSeparator))

        val params = HashMap<String, Any>()
        val args = joinPoint.args
        val parameterNames = signature.parameterNames
        for (i in args.indices) {
            addRequestParam(args[i], parameterNames[i], params)
        }
        logContent.append(String.format("%-25s: %s", "Provider request param", JsonUtil.encode(params) + lineSeparator))
        val startTime = System.currentTimeMillis()
        return try {
            val r: Any = onProcess(joinPoint)
            logContent.append(String.format("%-25s: %s", "Provider response param", JsonUtil.encode(r) + lineSeparator))
            logContent.append(String.format("%-25s: %sms", "Provider cost time", (System.currentTimeMillis() - startTime)))
            r
        } catch (ex: java.lang.Exception) {
            logContent.append(String.format("%-25s: %s", "Provider Error", ex.message + lineSeparator))
            logContent.append(String.format("%-25s: %sms", "Provider cost time", (System.currentTimeMillis() - startTime)))
            onException(log, ex)
        } finally {
            log.info(logContent.toString())
        }
    }

    @Throws(Throwable::class)
    private fun onProcess(joinPoint: ProceedingJoinPoint): Any {
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
            } else if ("request" != paramName && !contains(ignoreTypes, param)) {
                params[paramName] = param
            }
        }
    }

    private fun onException(log: Logger, ex: Exception): RestResult<Void> {
        log.error("Provider Error {}", ex, ex)
        return RestResultExt.failureRestResult("System error, please try later again!")
    }
}