package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.BasicStatusEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.definition.SystemConst.DEFAULT_CURRENCY
import tech.chaosmin.framework.module.cdpt.domain.enums.RateTableTypeEnum

/**
 * @author Romani min
 * @since 2020/12/23 21:35
 */
class ProductPlanEntity(id: Long? = null) : BaseEntity(id) {
    // 产品信息
    var partnerName: String? = null
    var productId: Long? = null
    var productCode: String? = null
    var productName: String? = null
    var waitingDays: Int? = null
    // 计划信息
    var planCode: String? = null
    var planName: String? = null
    var primaryCoverage: String? = null
    var currency: String? = null
    var comsRatio: Double? = null
    // TODO 这个是前端用来调整的佣金比例, 如何优化前端不让后端返回这个值
    var iComsRatio: Double? = null
    var status: BasicStatusEnum? = null
    // 计划责任及费率表
    var liabilities = mutableListOf<PlanLiabilityEntity>()
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

    fun addRateTable(startDay: Int, endDay: Int, amount: String) {
        synchronized(this) {
            val lia = PlanRateTableEntity().apply {
                this.modifyType = ModifyTypeEnum.SAVE
                this.type = RateTableTypeEnum.DAY
                this.dayStart = startDay
                this.dayEnd = endDay
                this.formula = "$0>=$startDay && $0<=$endDay"
                this.premium = amount.toDouble()
                this.premiumCurrency = DEFAULT_CURRENCY
            }
            rateTable.add(lia)
        }
    }
}