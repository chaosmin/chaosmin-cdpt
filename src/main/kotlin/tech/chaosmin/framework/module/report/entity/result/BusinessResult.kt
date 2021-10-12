/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * BusinessResult.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.report.entity.result

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

/**
 * @author Romani min
 * @since 2021/9/23 16:17
 */
class BusinessResult : ReportResult() {
    init {
        header = listOf(
            "订单编号", "保单号", "投保日期", "起保日期", "终保日期",
            "保险公司", "保险产品", "投保人", "保费", "手续费比例", "手续费", "保单状态", "支付方式"
        )
    }

    var detail: List<Detail>? = null

    class Detail {
        // 订单号
        var orderNo: String? = null

        // 保单号
        var policyNo: String? = null

        // 投保日期
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        var issueTime: Date? = null

        // 起保日期
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        var effectiveTime: Date? = null

        // 终保日期
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        var expiryTime: Date? = null

        // 保险公司
        var partnerName: String? = null

        // 保险产品
        var productName: String? = null

        // 投保人
        var issuerName: String? = null

        // 保费
        var premium: Double? = null

        // 手续费比例
        var comsRatio: String? = null

        // 手续费
        var coms: Double? = null

        // 保单状态
        var status: String? = null

        // 支付方式
        var payType: String? = null
    }
}