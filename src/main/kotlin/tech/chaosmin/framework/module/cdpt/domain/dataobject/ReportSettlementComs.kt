package tech.chaosmin.framework.module.cdpt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO

/**
 * 结算佣金表
 * SettlementCommissionTable
 *
 * @author Romani min
 * @since 2021/6/29 15:15
 */
class ReportSettlementComs(id: Long? = null) : BaseDO(id, 0) {
    // 统计开始日期
    var statisticsStartTime: Long? = null

    // 统计结束日期
    var statisticsEndTime: Long? = null

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
    var originalPrice: Double? = null

    // 结算价
    var settlementPrice: Double? = null

    // 折扣 %
    var discount: String? = null

    // 协议佣金比 %
    var agreementComsRatio: String? = null

    // 结算佣金比 %
    var settlementComsRatio: String? = null

    // 结算佣金
    var settlementComs: Double? = null
}