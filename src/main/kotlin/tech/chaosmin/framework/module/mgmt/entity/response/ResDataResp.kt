package tech.chaosmin.framework.module.mgmt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp

/**
 * @author Romani min
 * @since 2021/6/29 11:07
 */
@ApiModel("码表信息返回参数")
class ResDataResp : BaseResp() {
    @ApiModelProperty("类型")
    var itemKey: String? = null

    @ApiModelProperty("代码")
    var itemCode: String? = null

    @ApiModelProperty("名称")
    var itemName: String? = null
}