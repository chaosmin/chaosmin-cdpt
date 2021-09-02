package tech.chaosmin.framework.module.payment.entity.wechat

import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.enums.WechatRefundStatusEnum
import tech.chaosmin.framework.module.payment.entity.wechat.response.NativeRefundResp

/**
 * @author Romani min
 * @since 2021/9/2 20:38
 */
class RefundNotifyEntity {
    @ApiModelProperty("商户号", required = true, notes = "直连商户的商户号")
    var mchid: String? = null

    @ApiModelProperty("微信支付订单号", required = true, notes = "微信支付系统生成的订单号")
    var transaction_id: String? = null

    @ApiModelProperty("商户订单号", required = true, notes = "商户系统内部订单号")
    var out_trade_no: String? = null

    @ApiModelProperty("微信支付退款单号", required = true, notes = "微信退款单号")
    var refund_id: String? = null

    @ApiModelProperty("商户退款单号", required = true, notes = "商户退款单号")
    var out_refund_no: String? = null

    @ApiModelProperty("退款状态", required = true, notes = "退款状态")
    var refund_status: WechatRefundStatusEnum? = null

    @ApiModelProperty("退款成功时间", notes = "退款成功时间")
    var success_time: String? = null

    @ApiModelProperty("退款入账账户", required = true, notes = "取当前退款单的退款入账方")
    var user_received_account: String? = null

    @ApiModelProperty("金额信息", required = true, notes = "金额信息")
    var amount: NativeRefundResp.Amount? = null

    inner class Amount {
        @ApiModelProperty("订单金额", required = true, notes = "订单总金额，单位为分")
        var total: Long? = null

        @ApiModelProperty("退款金额", required = true, notes = "订单总金额，单位为分")
        var refund: Long? = null

        @ApiModelProperty("用户支付金额", required = true, notes = "现金支付金额，单位为分")
        var payer_total: Long? = null

        @ApiModelProperty("用户退款金额", required = true, notes = "退款给用户的金额，不包含所有优惠券金额")
        var payer_refund: Long? = null
    }
}