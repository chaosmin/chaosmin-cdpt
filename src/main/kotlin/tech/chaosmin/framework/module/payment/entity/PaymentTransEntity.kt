package tech.chaosmin.framework.module.payment.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.TradeChannelEnum
import tech.chaosmin.framework.base.enums.TransactionStatusEnum
import tech.chaosmin.framework.base.enums.WechatTradeTypeEnum
import java.util.*

/**
 * @author Romani min
 * @since 2021/8/25 11:10
 */
class PaymentTransEntity(id: Long? = null) : BaseEntity<PaymentTransEntity>(id) {
    var channel: TradeChannelEnum? = null
    var tradeType: WechatTradeTypeEnum? = null
    var status: TransactionStatusEnum? = null
    var transactionId: String? = null
    var outTradeNo: String? = null
    var description: String? = null
    var amount: Long? = null
    var refundAmount: Long? = null
    var payer: String? = null
    var refundAccount: String? = null
    var payUrl: String? = null
    var expireTime: Date? = null
    var orderTime: Date? = null
    var payTime: Date? = null
    var refundTime: Date? = null
    var closeTime: Date? = null
}