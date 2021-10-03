/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PaymentTransInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.payment.logic.interrogator

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.payment.domain.dataobject.PaymentTransaction
import tech.chaosmin.framework.module.payment.domain.service.PaymentTransService
import tech.chaosmin.framework.module.payment.entity.PaymentTransEntity
import tech.chaosmin.framework.module.payment.logic.convert.PaymentTransMapper

/**
 * @author Romani min
 * @since 2021/9/22 11:26
 */
@Component
class PaymentTransInterrogator(private val paymentTransService: PaymentTransService) : Interrogator<PaymentTransEntity, PaymentTransaction> {
    override fun getOne(id: Long): PaymentTransEntity? {
        val transaction = paymentTransService.getById(id)
        return PaymentTransMapper.INSTANCE.toEn(transaction)
    }

    fun getByOrderNo(orderNo: String): PaymentTransEntity? {
        val transaction = paymentTransService.getOne(Wrappers.query<PaymentTransaction>().eq("out_trade_no", orderNo))
        return PaymentTransMapper.INSTANCE.toEn(transaction)
    }

    override fun page(cond: PageQuery<PaymentTransaction>): IPage<PaymentTransEntity> {
        val page = paymentTransService.page(cond.page, cond.wrapper)
        return page.convert(PaymentTransMapper.INSTANCE::toEn)
    }
}