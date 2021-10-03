package tech.chaosmin.framework.module.payment.entity.wechat.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * @author Romani min
 * @since 2021/9/2 19:57
 */
@ApiModel("微信Native支付订单查询请求参数")
class NativeQueryReq {
    @ApiModelProperty("直连商户号", required = true, notes = "直连商户的商户号，由微信支付生成并下发")
    var mchid: String? = null

    @ApiModelProperty("微信支付订单号", required = true, notes = "微信支付系统生成的订单号")
    var transaction_id: String? = null
}