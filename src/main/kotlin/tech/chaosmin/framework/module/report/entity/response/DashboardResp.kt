package tech.chaosmin.framework.module.report.entity.response

import java.io.Serializable

/**
 * 首页统计数据
 *
 * @author Romani min
 * @since 2021/7/1 22:30
 */
class DashboardResp : Serializable {
    // 保单数据
    var policies: LineData = LineData()

    // 保费数据
    var premium: LineData = LineData()

    // 被保人数数据
    var insureds: LineData = LineData()

    // 出单员数据
    var issuers: LineData = LineData()

    inner class LineData {
        // 当前值
        var value: Double = 0.0

        // 期望值(上周)
        var expectedData = listOf<Double>()

        // 当前值(本周)
        var actualData = listOf<Double>()
    }
}