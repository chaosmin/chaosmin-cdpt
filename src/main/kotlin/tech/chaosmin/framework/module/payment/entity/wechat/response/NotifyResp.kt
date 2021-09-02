package tech.chaosmin.framework.module.payment.entity.wechat.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * @author Romani min
 * @since 2021/9/2 09:50
 */
@ApiModel("微信Native支付响应返回参数")
class NotifyResp {
    @ApiModelProperty("返回状态码", required = true, notes = "错误码，SUCCESS为清算机构接收成功，其他错误码为失败。")
    var code: String? = null

    @ApiModelProperty("返回信息", required = true, notes = "返回信息，如非空，为错误原因。")
    var message: String? = null
}