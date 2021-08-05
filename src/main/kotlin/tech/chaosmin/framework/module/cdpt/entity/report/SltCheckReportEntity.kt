package tech.chaosmin.framework.module.cdpt.entity.report

/**
 * @author Romani min
 * @since 2021/6/29 16:55
 */
class SltCheckReportEntity : ReportEntity() {
    // 总计被保人数
    var totalInsuredSize: Int? = null

    // 总计标准保费
    var totalPremium: Double? = null

    // 总计实收保费
    var actualPremium: Double? = null

    // 保险公司纬度标准保费合计
    var partnerList: List<PartnerPremium>? = null

    // 明细
    var detail: List<SltCheckEntity>? = null

    inner class PartnerPremium(var partnerName: String, var totalPremium: Double, var actualPremium: Double)

    fun doPartner() {
        this.partnerList = this.detail?.groupBy { it.partnerName!! }?.map { (g, l) ->
            PartnerPremium(g, l.sumByDouble { it.totalPremium ?: 0.0 }, l.sumByDouble { it.actualPremium ?: 0.0 })
        }
    }
}