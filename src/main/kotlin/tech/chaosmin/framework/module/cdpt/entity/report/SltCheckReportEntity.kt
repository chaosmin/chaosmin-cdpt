package tech.chaosmin.framework.module.cdpt.entity.report

import java.util.*

/**
 * @author Romani min
 * @since 2021/6/29 16:55
 */
class SltCheckReportEntity {
    // 统计开始日期
    var statisticsStartTime: Date? = null

    // 统计结束日期
    var statisticsEndTime: Date? = null

    // 总计被保人数
    var totalInsuredSize: Int? = null

    // 总计标准保费
    var totalPremium: Double? = null

    // 总计实收保费
    var actualPremium: Double? = null

    // 保险公司纬度标准保费合计
    var totalPremiumByPartner: Map<String, Double>? = null

    // 保险公司纬度实收保费合计
    var actualPremiumByPartner: Map<String, Double>? = null

    // 明细
    var detail: List<SltCheckEntity>? = null
}