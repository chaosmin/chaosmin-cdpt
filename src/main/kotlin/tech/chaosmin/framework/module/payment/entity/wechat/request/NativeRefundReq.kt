package tech.chaosmin.framework.module.payment.entity.wechat.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * @author Romani min
 * @since 2021/9/2 20:19
 */
@ApiModel("微信Native支付退款请求参数")
class NativeRefundReq {
    @ApiModelProperty("商户订单号", required = true, notes = "商户系统内部订单号")
    var out_trade_no: String? = null

    @ApiModelProperty("商户退款单号", required = true, notes = "商户系统内部的退款单号")
    var out_refund_no: String? = null

    @ApiModelProperty("退款原因", required = true, notes = "若商户传入，会在下发给用户的退款消息中体现退款原因")
    var reason: String? = null

    @ApiModelProperty("通知地址", required = true, notes = "通知URL必须为直接可访问的URL，不允许携带查询串")
    var notify_url: String? = null

    @ApiModelProperty("订单金额", required = true)
    var amount = Amount()

    inner class Amount {
        @ApiModelProperty("原订单金额", required = true, notes = "原支付交易的订单总金额，单位为分")
        var total: Int? = null

        @ApiModelProperty("退款金额", required = true, notes = "订单总金额，单位为分")
        var refund: Int? = null

        @ApiModelProperty("货币类型", notes = "CNY：人民币，境内商户号仅支持人民币", allowableValues = "CNY")
        var currency: String? = null
    }
}