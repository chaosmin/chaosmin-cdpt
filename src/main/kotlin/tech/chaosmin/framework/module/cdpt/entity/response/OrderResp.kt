package tech.chaosmin.framework.module.cdpt.entity.response

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp
import tech.chaosmin.framework.module.cdpt.domain.enums.OrderStatusEnum
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/27 16:33
 */
@ApiModel("订单接口返回参数")
class OrderResp : BaseResp() {
    @ApiModelProperty("订单号")
    var orderNo: String? = null

    @ApiModelProperty("产品计划ID")
    var productPlanId: Long? = null

    @ApiModelProperty("起保时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    var startTime: Date? = null

    @ApiModelProperty("停保时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    var endTime: Date? = null

    @ApiModelProperty("旅行目的地")
    var travelDestination: String? = null

    @ApiModelProperty("状态")
    var status: OrderStatusEnum? = null
}