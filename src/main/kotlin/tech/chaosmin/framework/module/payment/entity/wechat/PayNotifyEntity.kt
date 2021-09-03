package tech.chaosmin.framework.module.payment.entity.wechat

import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.enums.WechatTradeStateEnum
import tech.chaosmin.framework.base.enums.WechatTradeTypeEnum

/**
 * @author Romani min
 * @since 2021/9/2 09:53
 */
class PayNotifyEntity {
    @ApiModelProperty("应用ID", required = true, notes = "由微信生成的应用ID，全局唯一")
    var appid: String? = null

    @ApiModelProperty("商户号", required = true, notes = "直连商户的商户号")
    var mchid: String? = null

    @ApiModelProperty("商户订单号", required = true, notes = "商户系统内部订单号")
    var out_trade_no: String? = null

    @ApiModelProperty("微信支付订单号", required = true, notes = "微信支付系统生成的订单号")
    var transaction_id: String? = null

    @ApiModelProperty("交易类型", required = true, notes = "交易类型，枚举值")
    var trade_type: WechatTradeTypeEnum? = null

    @ApiModelProperty("交易状态", required = true, notes = "交易状态")
    var trade_state: WechatTradeStateEnum? = null

    @ApiModelProperty("交易状态描述", required = true, notes = "交易状态描述")
    var trade_state_desc: String? = null

    @ApiModelProperty("付款银行", required = true, notes = "付款银行")
    var bank_type: String? = null

    @ApiModelProperty("附加数据", notes = "附加数据")
    var attach: String? = null

    @ApiModelProperty("支付完成时间", required = true, notes = "支付完成时间")
    var success_time: String? = null

    @ApiModelProperty("支付者", required = true, notes = "支付者信息")
    var payer: Payer? = null

    @ApiModelProperty("支付金额", required = true, notes = "支付金额信息")
    var amount: Amount? = null

    inner class Payer {
        @ApiModelProperty("用户标识", required = true, notes = "用户在直连商户appid下的唯一标识")
        var openid: String? = null
    }

    inner class Amount {
        @ApiModelProperty("总金额", required = true, notes = "订单总金额，单位为分")
        var total: Long? = null

        @ApiModelProperty("用户支付金额", required = true, notes = "用户支付金额，单位为分")
        var payer_total: Long? = null

        @ApiModelProperty("货币类型", required = true, notes = "CNY：人民币，境内商户号仅支持人民币")
        var currency: String? = null

        @ApiModelProperty("用户支付币种", required = true, notes = "用户支付币种")
        var payer_currency: String? = null
    }
}