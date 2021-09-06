/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.payment.entity.wechat.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * @author Romani min
 * @since 2021/9/2 20:23
 */
@ApiModel("微信Native支付退款响应参数")
@JsonIgnoreProperties(ignoreUnknown = true)
class NativeRefundResp {
    @ApiModelProperty("微信支付退款单号", required = true, notes = "微信支付退款单号")
    var refund_id: String? = null

    @ApiModelProperty("商户退款单号", required = true, notes = "商户系统内部的退款单号")
    var out_refund_no: String? = null


    @ApiModelProperty("微信支付订单号", required = true, notes = "微信支付订单号")
    var transaction_id: String? = null


    @ApiModelProperty("商户订单号", required = true, notes = "商户系统内部订单号")
    var out_trade_no: String? = null

    @ApiModelProperty("退款渠道", required = true)
    var channel: String? = null

    @ApiModelProperty("退款入账账户", required = true, notes = "取当前退款单的退款入账方")
    var user_received_account: String? = null

    @ApiModelProperty("退款成功时间", notes = "退款成功时间，当退款状态为退款成功时有返回")
    var success_time: String? = null

    @ApiModelProperty("退款创建时间", required = true, notes = "退款受理时间")
    var create_time: String? = null

    @ApiModelProperty("退款状态", required = true, notes = "退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，可前往商户平台-交易中心，手动处理此笔退款")
    var status: String? = null

    @ApiModelProperty("金额信息", required = true, notes = "金额信息")
    var amount: Amount? = null

    @ApiModelProperty("资金账户", notes = "退款所使用资金对应的资金账户类型")
    var funds_account: String? = null

    inner class Amount {
        @ApiModelProperty("订单金额", required = true, notes = "订单总金额，单位为分")
        var total: Long? = null

        @ApiModelProperty("退款金额", required = true, notes = "订单总金额，单位为分")
        var refund: Long? = null

        @ApiModelProperty("用户支付金额", required = true, notes = "现金支付金额，单位为分")
        var payer_total: Long? = null

        @ApiModelProperty("用户退款金额", required = true, notes = "退款给用户的金额，不包含所有优惠券金额")
        var payer_refund: Long? = null

        @ApiModelProperty("应结退款金额", required = true, notes = "去掉非充值代金券退款金额后的退款金额，单位为分，退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额")
        var settlement_refund: Long? = null

        @ApiModelProperty("应结订单金额", required = true, notes = "应结订单金额=订单金额-免充值代金券金额，应结订单金额<=订单金额，单位为分")
        var settlement_total: Long? = null

        @ApiModelProperty("优惠退款金额", required = true, notes = "优惠退款金额<=退款金额，退款金额-代金券或立减优惠退款金额为现金，说明详见代金券或立减优惠，单位为分")
        var discount_refund: Long? = null

        @ApiModelProperty("货币类型", required = true, notes = "CNY：人民币，境内商户号仅支持人民币")
        var currency: String? = null

        @ApiModelProperty("退款出资账户及金额", notes = "退款出资账户及金额")
        var from: List<From>? = null

        inner class From {
            @ApiModelProperty("出资账户类型", notes = "出资账户类型")
            var account: String? = null

            @ApiModelProperty("出资金额", notes = "出资金额")
            var amount: Long? = null
        }
    }
}