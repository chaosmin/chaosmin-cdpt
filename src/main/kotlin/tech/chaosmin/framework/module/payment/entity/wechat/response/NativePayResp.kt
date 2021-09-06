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
 * @since 2021/8/23 16:48
 */
@ApiModel("微信Native支付下单返回参数")
@JsonIgnoreProperties(ignoreUnknown = true)
class NativePayResp {
    @ApiModelProperty("二维码链接", required = true, notes = "此URL用于生成支付二维码，然后提供给用户扫码支付")
    var code_url: String? = null
}