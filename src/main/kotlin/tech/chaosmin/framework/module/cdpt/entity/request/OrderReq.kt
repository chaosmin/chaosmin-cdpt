package tech.chaosmin.framework.module.cdpt.entity.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq
import tech.chaosmin.framework.module.cdpt.entity.enums.OrderStatusEnum

/**
 * @author Romani min
 * @since 2021/1/27 16:32
 */
@ApiModel("订单接口请求参数")
class OrderReq : BaseReq() {
    @ApiModelProperty("订单号")
    var orderNo: String? = null

    @ApiModelProperty("状态")
    var status: OrderStatusEnum? = null

    @ApiModelProperty("订单所属用户ID")
    var userId: Long? = null
}