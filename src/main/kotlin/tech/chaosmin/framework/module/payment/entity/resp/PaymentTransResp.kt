/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PaymentTransResp.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.payment.entity.resp

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp
import tech.chaosmin.framework.base.enums.TradeChannelEnum
import tech.chaosmin.framework.base.enums.TransactionStatusEnum
import tech.chaosmin.framework.base.enums.WechatTradeTypeEnum
import java.util.*

/**
 * @author Romani min
 * @since 2021/9/22 11:03
 */
@ApiModel("支付交易接口返回参数")
class PaymentTransResp : BaseResp() {
    @ApiModelProperty("支付渠道")
    var channel: TradeChannelEnum? = null

    @ApiModelProperty("支付方式")
    var tradeType: WechatTradeTypeEnum? = null

    @ApiModelProperty("支付状态")
    var status: TransactionStatusEnum? = null

    @ApiModelProperty("支付流水号")
    var transactionId: String? = null

    @ApiModelProperty("内部订单号")
    var outTradeNo: String? = null

    @ApiModelProperty("描述")
    var description: String? = null

    @ApiModelProperty("支付金额")
    var amount: Long? = null

    @ApiModelProperty("退款金额")
    var refundAmount: Long? = null

    @ApiModelProperty("支付人")
    var payer: String? = null

    @ApiModelProperty("退款账户")
    var refundAccount: String? = null

    @ApiModelProperty("支付链接")
    var payUrl: String? = null

    @ApiModelProperty("支付过期时间")
    var expireTime: Date? = null

    @ApiModelProperty("交易创建时间")
    var orderTime: Date? = null

    @ApiModelProperty("完成支付时间")
    var payTime: Date? = null

    @ApiModelProperty("退款时间")
    var refundTime: Date? = null

    @ApiModelProperty("交易关闭时间")
    var closeTime: Date? = null
}