package tech.chaosmin.framework.module.payment.domain.service.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.payment.domain.dao.PaymentTransactionDAO
import tech.chaosmin.framework.module.payment.domain.dataobject.PaymentTransaction
import tech.chaosmin.framework.module.payment.domain.service.PaymentTransService

/**
 * @author Romani min
 * @since 2021/8/25 13:40
 */
@Service
open class PaymentTransServiceImpl : ServiceImpl<PaymentTransactionDAO, PaymentTransaction>(), PaymentTransService {
    private val logger = LoggerFactory.getLogger(PaymentTransService::class.java)

    override fun updateByTradeNo(transaction: PaymentTransaction, outTradeNo: String) {
        val list = baseMapper.selectList(Wrappers.query<PaymentTransaction>().eq("out_trade_no", outTradeNo))
        if (list.isEmpty()) {
            logger.info("未找到相应的订单号[ $outTradeNo]的支付交易记录")
        } else if (list.size > 1) {
            logger.warn("该订单号[$outTradeNo]存在多笔交易记录, 请检查数据!")
        } else {
            val paymentTransaction = list.first()
//            if (TransactionStatusEnum.SUCCESS.getCode() == paymentTransaction.status) {
//                logger.error("该订单号[$outTradeNo]已支付成功, 无法更新状态!")
//                throw FrameworkException(ErrorCodeEnum.STATUS_ERROR.code, "支付记录状态已为成功")
//            }
            logger.info("订单号[$outTradeNo]的交易记录状态由(${paymentTransaction.status})变为(${transaction.status})")
            transaction.id = paymentTransaction.id
            baseMapper.updateById(transaction)
        }
    }
}