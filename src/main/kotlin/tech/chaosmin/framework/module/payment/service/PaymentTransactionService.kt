package tech.chaosmin.framework.module.payment.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.payment.domain.dataobject.PaymentTransaction

/**
 * @author Romani min
 * @since 2021/8/25 13:40
 */
interface PaymentTransactionService : IService<PaymentTransaction> {
    fun updateByTradeNo(transaction: PaymentTransaction, outTradeNo: String)
}