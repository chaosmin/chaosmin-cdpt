package tech.chaosmin.framework.domain.entity

import tech.chaosmin.framework.domain.const.SystemConst.DEFAULT_COMMISSION_RATIO
import tech.chaosmin.framework.domain.const.SystemConst.DEFAULT_CURRENCY
import tech.chaosmin.framework.domain.entity.base.BaseEntity
import tech.chaosmin.framework.domain.enums.BasicStatusEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum

/**
 * @author Romani min
 * @since 2020/12/23 21:35
 */
class ProductEntity(id: Long? = null) : BaseEntity(id) {
    var partnerId: Long? = null
    var partnerCode: String? = null
    var partnerName: String? = null

    var categoryIds: List<Long>? = null
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

    var plans = mutableListOf<ProductPlanEntity>()

    var specialAgreement: List<String>? = null
    var notice: List<String>? = null

    var numberOfPlan: Int? = null
    var noticeText: String? = null
    var noticeShort: String? = null

    fun addPlan(code: String, name: String, ratio: String?) {
        this.plans.add(ProductPlanEntity().apply {
            this.modifyType = ModifyTypeEnum.SAVE
            this.planCode = code
            this.planName = name
            this.currency = DEFAULT_CURRENCY
            this.defaultCommissionRatio = if (ratio.isNullOrBlank()) null else ratio.toDouble()
        })
    }

    fun getPlan(code: String?): ProductPlanEntity? {
        return if (code.isNullOrBlank()) null
        else this.plans.firstOrNull { it.planCode == code }
    }
}