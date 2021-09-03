package tech.chaosmin.framework.module.payment.entity.wechat.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * @author Romani min
 * @since 2021/8/23 16:47
 */
@ApiModel("微信Native支付下单请求参数")
class NativePayReq {
    @ApiModelProperty("应用ID", required = true, notes = "由微信生成的应用ID，全局唯一")
    var appid: String? = null

    @ApiModelProperty("直连商户号", required = true, notes = "直连商户的商户号")
    var mchid: String? = null

    @ApiModelProperty("商品描述", required = true, notes = "商品描述")
    var description: String? = null

    @ApiModelProperty("商户订单号", required = true, notes = "商户系统内部订单号")
    var out_trade_no: String? = null

    @ApiModelProperty("交易结束时间", notes = "格式为: YYYY-MM-DDTHH:mm:ss+TIMEZONE")
    var time_expire: String? = null

    @ApiModelProperty("附加数据")
    var attach: String? = null

    @ApiModelProperty("通知地址", required = true, notes = "通知URL必须为直接可访问的URL，不允许携带查询串")
    var notify_url: String? = null

    @ApiModelProperty("订单优惠标记")
    var goods_tag: String? = null

    @ApiModelProperty("订单金额", required = true)
    var amount: Amount? = null

    inner class Amount {
        @ApiModelProperty("总金额", required = true, notes = "订单总金额，单位为分")
        var total: Int? = null

        @ApiModelProperty("货币类型", notes = "CNY：人民币，境内商户号仅支持人民币", allowableValues = "CNY")
        var currency: String? = null
    }
}