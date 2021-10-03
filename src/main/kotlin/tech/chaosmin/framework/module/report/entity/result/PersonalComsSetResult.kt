/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PersonalComsSetResult.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.report.entity.result

import java.math.BigDecimal

/**
 * @author Romani min
 * @since 2021/9/10 16:49
 */
class PersonalComsSetResult : ReportResult() {
    init {
        header = listOf("产品名称", "保险公司", "原价", "结算价", "折扣", "协议佣金比", "结算佣金比", "结算佣金")
    }

    // 总原始保费
    var totalOriginalPremium: BigDecimal? = null

    // 总结算佣金
    var totalSettlementComs: BigDecimal? = null

    // 报表明细
    var detail: List<Detail>? = null

    class Detail {
        // 用户ID
        var userId: Long? = null

        // 用户姓名
        var userName: String? = null

        // 授权产品ID
        var goodsPlanId: Long? = null

        // 产品名称 [产品]-计划[主险保额]
        var goodsPlanName: String? = null

        // 保险公司
        var partnerName: String? = null

        // 原价
        var originalPremium: Double? = null

        // 结算价
        var settlementPremium: Double? = null

        // 折扣 %
        var discount: String? = null

        // 协议佣金比 %
        var agreementComsRatio: String? = null

        // 结算佣金比 %
        var settlementComsRatio: String? = null

        // 结算佣金
        var settlementComs: Double? = null
    }
}