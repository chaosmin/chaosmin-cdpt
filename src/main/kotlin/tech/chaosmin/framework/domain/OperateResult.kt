package tech.chaosmin.framework.domain

import java.io.Serializable

data class OperateResult<T>(
    var msg: String,
    var data: T? = null,
    var success: Boolean = true
) : Serializable

object OperateResultExt {

}