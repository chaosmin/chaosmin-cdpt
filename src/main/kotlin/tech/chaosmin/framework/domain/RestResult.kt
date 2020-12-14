package tech.chaosmin.framework.domain

import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import java.io.Serializable

data class RestResult<T>(
    var code: String,
    var msg: String? = null,
    var data: T? = null,
    var meta: Any = "",
    var success: Boolean = true
) : Serializable {
    fun mapper(result: RestResult<*>): RestResult<T> {
        this.success = result.success
        this.code = result.code
        this.msg = result.msg
        return this
    }

    fun success(data: T? = null): RestResult<T> {
        this.success = true
        this.data = data
        return this
    }
}

object RestResultExt {
    private const val OPT_SUCCESS = "Request success."
    private const val OPT_FAILED = "Request failed."
    private const val UNAUTHORIZED_OPERATION = "Unauthorized operation."
    private const val BAD_CREDENTIALS = "Bad credentials."

    fun <T> successRestResult() = RestResult<T>(ErrorCodeEnum.SUCCESS.code, OPT_SUCCESS)
    fun <T> successRestResult(msg: String) = RestResult<T>(ErrorCodeEnum.SUCCESS.code, msg)
    fun <T> successRestResult(data: T) = RestResult(ErrorCodeEnum.SUCCESS.code, OPT_SUCCESS, data)
    fun <T> successRestResult(data: T, meta: Any) = RestResult(ErrorCodeEnum.SUCCESS.code, OPT_SUCCESS, data, meta)
    fun <T> successRestResult(msg: String, data: T) = RestResult(ErrorCodeEnum.SUCCESS.code, msg, data)
    fun <T> successRestResult(msg: String, data: T, meta: Any) = RestResult(ErrorCodeEnum.SUCCESS.code, msg, data, meta)

    fun <T> failureRestResult() = RestResult<T>(ErrorCodeEnum.FAILURE.code, OPT_FAILED)
    fun <T> failureRestResult(msg: String) = RestResult<T>(ErrorCodeEnum.FAILURE.code, msg)
    fun <T> failureRestResult(data: T) = RestResult(ErrorCodeEnum.FAILURE.code, OPT_FAILED, data)
    fun <T> failureRestResult(data: T, meta: Any) = RestResult(ErrorCodeEnum.FAILURE.code, OPT_FAILED, data, meta)
    fun <T> failureRestResult(msg: String, data: T) = RestResult(ErrorCodeEnum.FAILURE.code, msg, data)
    fun <T> failureRestResult(msg: String, data: T, meta: Any) = RestResult(ErrorCodeEnum.FAILURE.code, msg, data, meta)

    fun noPermissionRestResult(msg: String = UNAUTHORIZED_OPERATION) =
        RestResult<Void>(ErrorCodeEnum.NO_PERMISSION.code, msg)

    fun badCredentialsRestResult(msg: String = BAD_CREDENTIALS) =
        RestResult<Void>(ErrorCodeEnum.AUTHENTICATION_FAILED.code, msg)
}