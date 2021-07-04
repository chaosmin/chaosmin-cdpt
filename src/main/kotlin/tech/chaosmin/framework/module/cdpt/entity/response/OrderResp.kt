package tech.chaosmin.framework.module.cdpt.entity.response

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp
import tech.chaosmin.framework.base.enums.OrderStatusEnum
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/27 16:33
 */
@ApiModel("订单接口返回参数")
class OrderResp : BaseResp() {
    @ApiModelProperty("订单号")
    var orderNo: String? = null

    @ApiModelProperty("实收保费")
    var actualPremium: Double? = null

    @ApiModelProperty("人数")
    var insuredSize: Double? = null

    @ApiModelProperty("生效")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    var effectiveTime: Date? = null

    @ApiModelProperty("止保时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    var expiryTime: Date? = null

    @ApiModelProperty("保险公司")
    var partnerName: String? = null

    @ApiModelProperty("出单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var issueTime: Date? = null

    @ApiModelProperty("出单人")
    var issuer: String? = null

    @ApiModelProperty("状态")
    var status: OrderStatusEnum? = null
}