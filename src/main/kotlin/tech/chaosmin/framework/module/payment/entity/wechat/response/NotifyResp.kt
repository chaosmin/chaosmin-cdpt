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
import tech.chaosmin.framework.base.enums.ErrorCodeEnum

/**
 * @author Romani min
 * @since 2021/9/2 09:50
 */
@ApiModel("微信Native支付响应返回参数")
@JsonIgnoreProperties(ignoreUnknown = true)
class NotifyResp {
    @ApiModelProperty("返回状态码", required = true, notes = "错误码，SUCCESS为清算机构接收成功，其他错误码为失败。")
    var code: String? = null

    @ApiModelProperty("返回信息", required = true, notes = "返回信息，如非空，为错误原因。")
    var message: String? = null

    companion object {
        fun success(): NotifyResp {
            return NotifyResp().apply {
                this.code = ErrorCodeEnum.SUCCESS.code
                this.message = ErrorCodeEnum.SUCCESS.msg
            }
        }

        fun failed(error: ErrorCodeEnum, msg: String? = null): NotifyResp {
            return NotifyResp().apply {
                this.code = error.code
                this.message = msg ?: error.msg
            }
        }
    }
}