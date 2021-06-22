package tech.chaosmin.framework.module.cdpt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp

/**
 * @author Romani min
 * @since 2021/6/22 23:44
 */
@ApiModel("投保人返回参数")
class PolicyHolderResp : BaseResp() {
    @ApiModelProperty("姓名")
    var name: String? = null
}