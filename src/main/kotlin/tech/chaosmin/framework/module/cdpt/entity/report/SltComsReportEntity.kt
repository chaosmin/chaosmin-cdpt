package tech.chaosmin.framework.module.cdpt.entity.report

/**
 * 个人佣金结算表汇总信息
 *
 * @author Romani min
 * @since 2021/6/29 15:52
 */
class SltComsReportEntity : ReportEntity() {
    // 总计原价
    var totalOriginalPrice: Double? = null

    // 总计结算价
    var totalSettlementPrice: Double? = null

    // 报表明细
    var detail: List<SltComsEntity>? = null
}