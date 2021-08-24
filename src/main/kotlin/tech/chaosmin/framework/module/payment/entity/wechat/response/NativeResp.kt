package tech.chaosmin.framework.module.payment.entity.wechat.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * @author Romani min
 * @since 2021/8/23 16:48
 */
@ApiModel("微信Native支付下单返回参数")
class NativeResp {
    @ApiModelProperty("二维码链接", required = true, notes = "此URL用于生成支付二维码，然后提供给用户扫码支付")
    var code_url: String? = null
}