package tech.chaosmin.framework.module.cdpt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp

/**
 * @author Romani min
 * @since 2021/6/9 09:58
 */
@ApiModel("保单可回溯信息返回参数")
class PolicyKhsResp : BaseResp() {
    @ApiModelProperty("保单ID")
    var policyId: Long? = null

    @ApiModelProperty("可回溯材料类型")
    var khsType: String? = null

    @ApiModelProperty("资源地址")
    var resourceUrl: String? = null
}