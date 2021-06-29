package tech.chaosmin.framework.module.cdpt.entity.report

import java.util.*

/**
 * 个人佣金结算表汇总信息
 *
 * @author Romani min
 * @since 2021/6/29 15:52
 */
class SltComsReportEntity {
    // 统计开始日期
    var statisticsStartTime: Date? = null

    // 统计结束日期
    var statisticsEndTime: Date? = null

    // 用户ID
    var userId: Long? = null

    // 总计原价
    var totalOriginalPrice: Double? = null

    // 总计结算价
    var totalSettlementPrice: Double? = null

    // 报表明细
    var detail: List<SltComsEntity>? = null
}