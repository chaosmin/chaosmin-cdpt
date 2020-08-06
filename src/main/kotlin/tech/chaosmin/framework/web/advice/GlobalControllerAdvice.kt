package tech.chaosmin.framework.web.advice

import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt.failureRestResult
import tech.chaosmin.framework.domain.RestResultExt.noPermissionRestResult
import tech.chaosmin.framework.exception.AuthenticationException
import tech.chaosmin.framework.exception.PermissionException
import tech.chaosmin.framework.exception.ResourceNotExistException

@Order(1)
@RestControllerAdvice
class GlobalControllerAdvice {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * 404异常
     */
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(e: NoHandlerFoundException): RestResult<Void> {
        logger.error("捕获到NoHandlerFoundException", e)
        val message = "API: ${e.requestURL} url not found"
        return failureRestResult(message)
    }

    /**
     * Method Not Supported
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): RestResult<Void> {
        logger.error("捕获到HttpRequestMethodNotSupportedException", e)
        return failureRestResult(e.message ?: "http method not supported")
    }

    /**
     * 请求参数异常
     */
    @ExceptionHandler(HttpMessageConversionException::class)
    fun handleHttpMessageConversionException(e: HttpMessageConversionException): RestResult<Void> {
        logger.error("捕获到请求参数异常", e)
        return failureRestResult(e.message ?: "invalid params")
    }

    /**
     * 请求参数缺失
     */
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(e: MissingServletRequestParameterException): RestResult<Void> {
        logger.error("捕获到请求参数缺失异常", e)
        return failureRestResult(e.message ?: "incomplete params")
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): RestResult<Void> {
        val message = e.bindingResult.allErrors.joinToString { "${it.defaultMessage};" }
        logger.error("捕获到参数校验异常: {}", message, e)
        return failureRestResult(message)
    }

    /**
     * 参数校验异常
     */
    // @ExceptionHandler(ConstraintViolationException::class)
    // fun handleConstraintViolationException(e: ConstraintViolationException): RestResult<Void> {
    //     logger.error("捕获到参数校验异常", e)
    //     return failureRestResult(e.message ?: "params validation failed")
    // }

    /**
     * 参数绑定异常
     */
    @ExceptionHandler(BindException::class)
    fun handleBindException(e: BindException): RestResult<Void> {
        val message = e.bindingResult.allErrors.joinToString { "${it.defaultMessage};" }
        logger.error("捕获到参数绑定异常: {}", message, e)
        return failureRestResult(message)
    }

    /**
     * 资源不存在异常
     */
    @ExceptionHandler(ResourceNotExistException::class)
    fun handleResourceNotExistException(e: ResourceNotExistException): RestResult<Void> {
        logger.error("捕获到资源不存在异常", e)
        return failureRestResult(e.message ?: "resource not exist")
    }

    /**
     * 认证失败, token无效
     */
    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(e: AuthenticationException): RestResult<Void> {
        logger.error("捕获到认证异常", e)
        return RestResult(RestResult.TOKEN_INVALID_CODE, e.message ?: "invalid token")
    }

    /**
     * 无权操作
     */
    @ExceptionHandler(PermissionException::class)
    fun handlePermissionException(e: PermissionException): RestResult<Void> {
        logger.error("捕获到权限异常", e)
        return noPermissionRestResult(e.message ?: "permission deny")
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(Throwable::class)
    fun handleThrowable(e: Throwable): RestResult<Void> {
        logger.error("捕获到未知异常: {}", e.javaClass.name)
        logger.error(e.message, e)
        return failureRestResult(e.message ?: "unknown error")
    }
}