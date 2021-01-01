package tech.chaosmin.framework.base

import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

/**
 * @author Romani min
 * @since 2020/12/23 18:21
 */
open class BaseReq : Serializable {
    @ApiModelProperty(value = "扩展信息")
    var extraInfo: String? = null
}