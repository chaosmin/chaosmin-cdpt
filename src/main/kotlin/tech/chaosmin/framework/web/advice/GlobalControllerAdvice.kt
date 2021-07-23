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
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt.failureRestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.exception.PermissionException
import tech.chaosmin.framework.exception.ResourceNotExistException
import javax.validation.ConstraintViolationException

@Order(1)
@RestControllerAdvice
class GlobalControllerAdvice {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * 404异常
     */
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(e: NoHandlerFoundException): RestResult<String> {
        logger.error("捕获到NoHandlerFoundException", e)
        val message = "API: ${e.requestURL} url not found"
        return failureRestResult(msg = message)
    }

    /**
     * Method Not Supported
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): RestResult<String> {
        logger.error("捕获到HttpRequestMethodNotSupportedException", e)
        return failureRestResult(msg = e.message ?: "http method not supported")
    }

    /**
     * 请求参数异常
     */
    @ExceptionHandler(HttpMessageConversionException::class)
    fun handleHttpMessageConversionException(e: HttpMessageConversionException): RestResult<String> {
        logger.error("捕获到请求参数异常", e)
        return failureRestResult(msg = e.message ?: "invalid parameters")
    }

    /**
     * 请求参数缺失
     */
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(e: MissingServletRequestParameterException): RestResult<String> {
        logger.error("捕获到请求参数缺失异常", e)
        return failureRestResult(msg = e.message)
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): RestResult<String> {
        val message = e.bindingResult.allErrors.joinToString { "${it.defaultMessage};" }
        logger.error("捕获到参数校验异常: {}", message, e)
        return failureRestResult(msg = message)
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): RestResult<String> {
        logger.error("捕获到参数校验异常", e)
        return failureRestResult(msg = e.message ?: "params validation failed")
    }

    /**
     * 参数绑定异常
     */
    @ExceptionHandler(BindException::class)
    fun handleBindException(e: BindException): RestResult<String> {
        val message = e.bindingResult.allErrors.joinToString { "${it.defaultMessage};" }
        logger.error("捕获到参数绑定异常: {}", message, e)
        return failureRestResult(msg = message)
    }

    /**
     * 资源不存在异常
     */
    @ExceptionHandler(ResourceNotExistException::class)
    fun handleResourceNotExistException(e: ResourceNotExistException): RestResult<String> {
        logger.error("捕获到资源不存在异常", e)
        return failureRestResult(msg = e.message ?: "resource not exist")
    }

    /**
     * 无权操作
     */
    @ExceptionHandler(PermissionException::class)
    fun handlePermissionException(e: PermissionException): RestResult<String> {
        logger.error("捕获到权限异常", e)
        return RestResult(ErrorCodeEnum.NO_PERMISSION.code)
    }

    /**
     * 框架异常
     */
    @ExceptionHandler(FrameworkException::class)
    fun handleFrameworkException(e: FrameworkException): RestResult<String> {
        logger.error("捕获到框架异常", e)
        return failureRestResult(e)
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(Throwable::class)
    fun handleThrowable(e: Throwable): RestResult<String> {
        logger.error("捕获到未知异常: {}", e.javaClass.name)
        logger.error(e.message, e)
        return failureRestResult(msg = e.message ?: "unknown error")
    }
}