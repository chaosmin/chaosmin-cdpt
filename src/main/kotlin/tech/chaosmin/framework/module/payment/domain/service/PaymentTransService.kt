package tech.chaosmin.framework.module.payment.domain.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.payment.domain.dataobject.PaymentTransaction

/**
 * @author Romani min
 * @since 2021/8/25 13:40
 */
interface PaymentTransService : IService<PaymentTransaction> {
    fun updateByTradeNo(transaction: PaymentTransaction, outTradeNo: String)
}