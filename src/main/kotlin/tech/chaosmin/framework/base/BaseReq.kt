package tech.chaosmin.framework.base

import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.utils.JsonUtil
import java.io.Serializable

/**
 * @author Romani min
 * @since 2020/12/23 18:21
 */
open class BaseReq : Serializable {
    @ApiModelProperty("扩展信息")
    var extraInfo: String? = null

    override fun toString(): String {
        return JsonUtil.encode(this)
    }
}