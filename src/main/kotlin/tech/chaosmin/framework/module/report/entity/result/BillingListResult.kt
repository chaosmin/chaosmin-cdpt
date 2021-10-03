/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * BillingListResult.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.report.entity.result

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

/**
 * @author Romani min
 * @since 2021/9/10 15:49
 */
class BillingListResult : ReportResult() {
    init {
        header = listOf(
            "No.", "保单号", "产品名称",
            "人数", "标准单价", "标准保费", "实收单价", "实收保费",
            "生效时间", "终止时间", "出单时间",
            "团号", "保险公司", "出单人", "目的地"
        )
    }

    // 总计被保人数
    var totalInsuredSize: Int? = null

    // 总计标准保费
    var totalPremium: Double? = null

    // 总计实收保费
    var actualPremium: Double? = null

    // 保险公司纬度标准保费合计
    var partnerList: List<PartnerPremium>? = null

    var detail: List<Detail>? = null

    fun groupByPartner() {
        this.partnerList = this.detail?.groupBy { it.partnerName!! }?.map { (g, l) ->
            PartnerPremium(g, l.sumByDouble { it.totalPremium ?: 0.0 }, l.sumByDouble { it.actualPremium ?: 0.0 })
        }
    }

    inner class PartnerPremium(var partnerName: String, var totalPremium: Double, var actualPremium: Double)

    class Detail {
        // 订单号
        var orderNo: String? = null

        // 保单号
        var policyNo: String? = null

        // 产品名称
        var goodsPlanName: String? = null

        // 人数
        var insuredSize: Int? = null

        // 标准单价
        var unitPremium: Double? = null

        // 标准保费
        var totalPremium: Double? = null

        // 实收单价
        var actualUnitPremium: Double? = null

        // 实收保费
        var actualPremium: Double? = null

        // 生效时间
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        var effectiveTime: Date? = null

        // 终止时间
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        var expiryTime: Date? = null

        // 出单时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        var issueTime: Date? = null

        // 团号
        var groupNo: String? = null

        // 保险公司
        var partnerName: String? = null

        // 出单人
        var issuer: String? = null

        // 目的地
        var address: String? = null
    }

}