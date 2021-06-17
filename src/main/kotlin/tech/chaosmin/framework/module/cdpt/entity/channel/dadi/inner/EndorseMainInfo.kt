package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import java.math.BigDecimal

/**
 * @author Romani min
 * @since 2021/6/17 23:26
 */
class EndorseMainInfo {
    // 保单号
    var policyNo: String? = null

    // 投保单号
    var proposalNo: String? = null

    // 单证流水号
    var printNo: String? = null

    // 原渠道交易流水号
    var originTradeSerialNo: String? = null

    // 批改日期
    var applyDate: String? = null

    // 批改生效日期
    var endoEffectiveDate: String? = null

    // 原渠道交易日期
    var oldTransExeTime: String? = null

    // 原保单保费
    var amount: BigDecimal? = null
}