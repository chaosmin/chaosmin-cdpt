package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.BasicStatusEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.definition.SystemConst.DEFAULT_CURRENCY

/**
 * @author Romani min
 * @since 2020/12/23 21:35
 */
class ProductEntity(id: Long? = null) : BaseEntity(id) {
    var partnerId: Long? = null
    var partnerCode: String? = null
    var partnerName: String? = null

    var productCategoryId: Long? = null
    var categoryName: String? = null
    var categorySubName: String? = null

    var productCode: String? = null
    var productName: String? = null
    var productSubName: String? = null
    var partnerProductNo: String? = null
    var waitingDays: String? = null
    var productDesc: String? = null
    var productRatio: String? = null
    var status: BasicStatusEnum? = null
    var clauseUrl: String? = null

    var plans = mutableListOf<ProductPlanEntity>()

    var insuranceNotice: String? = null
    var externalText: String? = null

    var numberOfPlan: Int? = null

    fun addPlan(code: String, name: String, ratio: String?) {
        this.plans.add(ProductPlanEntity().apply {
            this.modifyType = ModifyTypeEnum.SAVE
            this.status = BasicStatusEnum.ENABLED
            this.planCode = code
            this.planName = name
            this.currency = DEFAULT_CURRENCY
            this.comsRatio = if (ratio.isNullOrBlank()) null else ratio.toDouble()
        })
    }

    fun getPlan(code: String?): ProductPlanEntity? {
        return if (code.isNullOrBlank()) null
        else this.plans.firstOrNull { it.planCode == code }
    }
}