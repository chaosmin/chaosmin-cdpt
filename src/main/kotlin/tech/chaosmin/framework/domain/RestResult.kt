package tech.chaosmin.framework.domain

import java.io.Serializable

data class RestResult<T>(
    var code: Int,
    var msg: String,
    var data: T? = null,
    var meta: Any = "",
    var success: Boolean = true
) : Serializable {
    companion object {
        const val SUCCESS_CODE = 200
        const val TOKEN_INVALID_CODE = 401
        const val NO_PERMISSION_CODE = 444
        const val FAILURE_CODE = 500
    }
}

object RestResultExt {
    private const val OPT_SUCCESS = "Request success."
    private const val OPT_FAILED = "Request failed."
    private const val UNAUTHORIZED_OPERATION = "Unauthorized operation."

    fun <T> successRestResult() = RestResult<T>(RestResult.SUCCESS_CODE, OPT_SUCCESS)
    fun <T> successRestResult(msg: String) = RestResult<T>(RestResult.SUCCESS_CODE, msg)
    fun <T> successRestResult(data: T) = RestResult(RestResult.SUCCESS_CODE, OPT_SUCCESS, data)
    fun <T> successRestResult(data: T, meta: Any) = RestResult(RestResult.SUCCESS_CODE, OPT_SUCCESS, data, meta)
    fun <T> successRestResult(msg: String, data: T) = RestResult(RestResult.SUCCESS_CODE, msg, data)
    fun <T> successRestResult(msg: String, data: T, meta: Any) = RestResult(RestResult.SUCCESS_CODE, msg, data, meta)

    fun <T> failureRestResult() = RestResult<T>(RestResult.FAILURE_CODE, OPT_FAILED)
    fun <T> failureRestResult(msg: String) = RestResult<T>(RestResult.FAILURE_CODE, msg)
    fun <T> failureRestResult(data: T) = RestResult(RestResult.FAILURE_CODE, OPT_FAILED, data)
    fun <T> failureRestResult(data: T, meta: Any) = RestResult(RestResult.FAILURE_CODE, OPT_FAILED, data, meta)
    fun <T> failureRestResult(msg: String, data: T) = RestResult(RestResult.FAILURE_CODE, msg, data)
    fun <T> failureRestResult(msg: String, data: T, meta: Any) = RestResult(RestResult.FAILURE_CODE, msg, data, meta)

    fun <T> noPermissionRestResult() = RestResult<T>(RestResult.NO_PERMISSION_CODE, UNAUTHORIZED_OPERATION)
    fun <T> noPermissionRestResult(msg: String) = RestResult<T>(RestResult.NO_PERMISSION_CODE, msg)
    fun <T> noPermissionRestResult(data: T) = RestResult(RestResult.NO_PERMISSION_CODE, UNAUTHORIZED_OPERATION, data)
    fun <T> noPermissionRestResult(data: T, meta: Any) = RestResult(RestResult.NO_PERMISSION_CODE, UNAUTHORIZED_OPERATION, data, meta)

    fun <T> noPermissionRestResult(msg: String, data: T) = RestResult(RestResult.NO_PERMISSION_CODE, msg, data)
    fun <T> noPermissionRestResult(msg: String, data: T, meta: Any) = RestResult(RestResult.NO_PERMISSION_CODE, msg, data, meta)
}