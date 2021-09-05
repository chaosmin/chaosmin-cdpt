package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.base.enums.RateTableTypeEnum
import tech.chaosmin.framework.base.enums.StatusEnum
import tech.chaosmin.framework.definition.SystemConst.DEFAULT_CURRENCY

/**
 * 保险产品计划信息实体对象 <p>
 * @author Romani min
 * @since 2020/12/23 21:35
 */
class ProductPlanEntity(id: Long? = null) : BaseEntity<ProductPlanEntity>(id) {
    var comsRatio: Double? = null
    var currency: String? = null

    // TODO 这个是前端用来调整的佣金比例, 如何优化前端不让后端返回这个值
    var iComsRatio: Double? = null
    var liabilities = mutableListOf<ProductPlanLibEntity>()
    var partnerName: String? = null
    var planCode: String? = null
    var planName: String? = null
    var primaryCoverage: String? = null
    var productCode: String? = null
    var productId: Long? = null
    var productName: String? = null
    var rateTable = mutableListOf<ProductPlanRaTeEntity>()
    var status: StatusEnum? = null

    fun addLiability(category: String, liability: String, amount: String) {
        synchronized(this) {
            val lia = ProductPlanLibEntity().apply {
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
            val lia = ProductPlanRaTeEntity().apply {
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