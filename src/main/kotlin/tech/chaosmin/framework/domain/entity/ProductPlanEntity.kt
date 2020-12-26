package tech.chaosmin.framework.domain.entity

import tech.chaosmin.framework.domain.const.SystemConst.DEFAULT_CURRENCY
import tech.chaosmin.framework.domain.entity.base.BaseEntity
import tech.chaosmin.framework.domain.enums.BasicStatusEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.domain.enums.RateTableTypeEnum

/**
 * @author Romani min
 * @since 2020/12/23 21:35
 */
class ProductPlanEntity(id: Long? = null) : BaseEntity(id) {
    var productId: Long? = null
    var planCode: String? = null
    var planName: String? = null
    var primaryCoverage: String? = null
    var currency: String? = null
    var defaultCommissionRatio: Double? = null
    var status: BasicStatusEnum? = null

    var liabilities = mutableListOf<ProductPlanLiabilityEntity>()
    var rateTable = mutableListOf<ProductPlanRateTableEntity>()

    fun addLiability(category: String, liability: String, amount: String) {
        synchronized(this) {
            val lia = ProductPlanLiabilityEntity().apply {
                this.modifyType = ModifyTypeEnum.SAVE
                this.liabilityCategory = category
                this.liabilityName = liability
                this.amount = amount
            }
            liabilities.add(lia)
        }
    }

    fun addRateTable(startDay: String, endDay: String, amount: String) {
        synchronized(this) {
            val lia = ProductPlanRateTableEntity().apply {
                this.modifyType = ModifyTypeEnum.SAVE
                this.type = RateTableTypeEnum.DAY
                this.factor = "days"
                this.formula = "#{$factor} >= $startDay && #{$factor} <= $endDay"
                this.premium = amount.toDouble()
                this.premiumCurrency = DEFAULT_CURRENCY
            }
            rateTable.add(lia)
        }
    }
}