package tech.chaosmin.framework.module.payment.domain.dataobject

import tech.chaosmin.framework.base.BaseDO
import java.util.*

/**
 * 微信Native支付订单记录
 *
 * @author Romani min
 * @since 2021/8/24 19:01
 */
open class PaymentTransaction(id: Long? = null) : BaseDO(id, 0) {
    // 支付渠道(1-微信 2-支付宝 3-银行卡)
    var channel: Int? = null

    // 交易单状态(0-初始化 1-支付中 2-支付成功 3-支付失败)
    var status: Int? = null

    // 交易类型
    var tradeType: Int? = null

    // 交易流水号
    var transactionId: String? = null

    // 外部交易号(同订单号 order_no)
    var outTradeNo: String? = null

    // 订单描述
    var description: String? = null

    // 支付金额
    var amount: Long? = null

    // 支付链接
    var payUrl: String? = null

    // 付款人
    var payer: String? = null

    // 支付单过期时间
    var expireTime: Date? = null

    // 订单时间
    var orderTime: Date? = null

    // 支付时间(收到微信支付成功回调)
    var payTime: Date? = null

    // 退款时间
    var refundTime: Date? = null

    // 关闭时间
    var closeTime: Date? = null
}