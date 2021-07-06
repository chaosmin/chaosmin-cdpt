package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.base.enums.RateTableTypeEnum
import tech.chaosmin.framework.base.enums.StatusEnum
import tech.chaosmin.framework.definition.SystemConst.DEFAULT_CURRENCY

/**
 * @author Romani min
 * @since 2020/12/23 21:35
 */
class ProductPlanEntity(id: Long? = null) : BaseEntity(id) {
    // 保险公司
    var partnerName: String? = null

    // 产品ID
    var productId: Long? = null

    // 产品编号
    var productCode: String? = null

    // 产品名称
    var productName: String? = null

    // 计划信息
    var planCode: String? = null

    // 计划名称
    var planName: String? = null

    // 主险保额
    var primaryCoverage: String? = null

    // 币种
    var currency: String? = null

    // 佣金比例
    var comsRatio: Double? = null

    // 设置佣金比例
    // TODO 这个是前端用来调整的佣金比例, 如何优化前端不让后端返回这个值
    var iComsRatio: Double? = null

    // 产品计划状态
    var status: StatusEnum? = null

    // 计划责任
    var liabilities = mutableListOf<PlanLiabilityEntity>()

    // 计划费率表
    var rateTable = mutableListOf<PlanRateTableEntity>()

    fun addLiability(category: String, liability: String, amount: String) {
        synchronized(this) {
            val lia = PlanLiabilityEntity().apply {
                this.modifyType = ModifyTypeEnum.SAVE
                this.liabilityCategory = category
                this.liabilityName = liability
                this.amount = amount
            }
            liabilities.add(lia)
        }
    }

    fun addRateTable(startDay: Int, endDay: Int, amount: String, remark: String) {
        synchronized(this) {
            val lia = PlanRateTableEntity().apply {
                this.modifyType = ModifyTypeEnum.SAVE
                this.type = RateTableTypeEnum.DAY
                this.dayStart = startDay
                this.dayEnd = endDay
                this.formula = "$0>=$startDay && $0<=$endDay"
                this.premium = amount.toDouble()
                this.premiumCurrency = DEFAULT_CURRENCY
                this.remark = remark
            }
            rateTable.add(lia)
        }
    }
}