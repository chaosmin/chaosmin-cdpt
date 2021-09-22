/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * OrderPaymentResp.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

/**
 * @author Romani min
 * @since 2021/9/22 10:34
 */
@ApiModel("个人产品计划管理接口返回参数")
class OrderPaymentResp : Serializable {
    @ApiModelProperty("订单号")
    var orderNo: String? = null

    @ApiModelProperty("支付金额")
    var amount: String? = null

    @ApiModelProperty("支付链接")
    var payUrl: String? = null
}