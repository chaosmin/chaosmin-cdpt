/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PaymentTransProvider.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.payment.api.provider

import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.payment.api.PaymentTransAPI
import tech.chaosmin.framework.module.payment.api.convert.PaymentTransConvert
import tech.chaosmin.framework.module.payment.entity.PaymentTransEntity
import tech.chaosmin.framework.module.payment.entity.resp.PaymentTransResp
import tech.chaosmin.framework.module.payment.logic.interrogator.PaymentTransInterrogator

/**
 * @author Romani min
 * @since 2021/9/22 11:07
 */
@RestController
open class PaymentTransProvider(private val paymentTransInterrogator: PaymentTransInterrogator) : PaymentTransAPI {
    override fun searchTrade(orderNo: String): RestResult<PaymentTransResp> {
        val paymentTrans = paymentTransInterrogator.getByOrderNo(orderNo) ?: PaymentTransEntity()
        val response = PaymentTransConvert.INSTANCE.toResp(paymentTrans)
        return RestResultExt.successRestResult(response)
    }
}