package tech.chaosmin.framework.module.cdpt.entity.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq
import tech.chaosmin.framework.module.cdpt.entity.enums.PolicyStatusEnum
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/27 16:32
 */
@ApiModel("保单接口请求参数")
class PolicyReq : BaseReq() {
    @ApiModelProperty("订单号")
    var orderNo: String? = null

    @ApiModelProperty("保单号")
    var policyNo: String? = null

    @ApiModelProperty("产品计划ID")
    var productPlanId: Long? = null

    @ApiModelProperty("起保时间")
    var effectiveTime: Date? = null

    @ApiModelProperty("停保时间")
    var expiryTime: Date? = null

    @ApiModelProperty("旅行目的地")
    var travelDestination: String? = null

    @ApiModelProperty("保单状态")
    var status: PolicyStatusEnum? = null
}