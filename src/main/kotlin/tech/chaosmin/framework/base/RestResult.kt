package tech.chaosmin.framework.base

import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.FrameworkException
import java.io.Serializable

data class RestResult<T>(
    var code: String,
    var msg: String? = null,
    var data: T? = null,
    var meta: Any = "",
    var success: Boolean = true
) : Serializable {
    fun mapper(result: RestResult<*>): RestResult<T> = this.apply {
        this.success = result.success
        this.code = result.code
        this.msg = result.msg
    }

    fun take(data: T): RestResult<T> = this.apply {
        this.data = data
    }

    fun success(data: T? = null): RestResult<T> = this.apply {
        this.success = true
        this.data = data
    }

    fun convert(function: () -> T): RestResult<T> = this.apply {
        this.data = function()
    }
}

object RestResultExt {
    private const val OPT_SUCCESS = "Request success."
    private const val OPT_FAILED = "Request failed."
    private const val UNAUTHORIZED_OPERATION = "Unauthorized operation."
    private const val BAD_CREDENTIALS = "Bad credentials."

    fun <T> mapper(result: RestResult<*>): RestResult<T> = RestResult(result.code, result.msg, success = result.success)
    fun <T> successRestResult() = RestResult<T>(ErrorCodeEnum.SUCCESS.code)
    fun <T> successRestResult(msg: String) = RestResult<T>(ErrorCodeEnum.SUCCESS.code, msg)
    fun <T> successRestResult(data: T) = RestResult(ErrorCodeEnum.SUCCESS.code, OPT_SUCCESS, data)
    fun <T> successRestResult(data: T, meta: Any) = RestResult(ErrorCodeEnum.SUCCESS.code, OPT_SUCCESS, data, meta)
    fun <T> successRestResult(msg: String, data: T) = RestResult(ErrorCodeEnum.SUCCESS.code, msg, data)
    fun <T> successRestResult(msg: String, data: T, meta: Any) = RestResult(ErrorCodeEnum.SUCCESS.code, msg, data, meta)

    fun <T> failureRestResult() = RestResult<T>(ErrorCodeEnum.FAILURE.code, OPT_FAILED)
    fun <T> failureRestResult(msg: String?) = RestResult<T>(ErrorCodeEnum.FAILURE.code, msg)
    fun <T> failureRestResult(msg: String, data: T) = RestResult(ErrorCodeEnum.FAILURE.code, msg, data)

    fun failureRestResult(e: FrameworkException) = RestResult<String>(e.errCode ?: ErrorCodeEnum.FAILURE.code, e.getMsg())
}