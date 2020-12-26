package tech.chaosmin.framework.domain.entity

import tech.chaosmin.framework.domain.const.SystemConst.DEFAULT_COMMISSION_RATIO
import tech.chaosmin.framework.domain.const.SystemConst.DEFAULT_CURRENCY
import tech.chaosmin.framework.domain.entity.base.BaseEntity
import tech.chaosmin.framework.domain.enums.BasicStatusEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.utils.StringUtil.getCodeFromZh

/**
 * @author Romani min
 * @since 2020/12/23 21:35
 */
class ProductEntity(id: Long? = null) : BaseEntity(id) {
    var partnerId: Long? = null
    var partnerName: String? = null
    var categoryIds: List<Long>? = null
    var productCode: String? = null
    var productName: String? = null
    var productSubName: String? = null
    var partnerProductNo: String? = null
    var productDesc: String? = null
    var status: BasicStatusEnum? = null

    var plans = mutableListOf<ProductPlanEntity>()
    var specialAgreement: List<String>? = null
    var notice: List<String>? = null

    var numberOfPlan: Int? = null

    fun getOrCreatePlan(planName: String?): ProductPlanEntity {
        synchronized(this) {
            var plan = plans.firstOrNull { it.planName == planName }
            if (plan == null) {
                plan = ProductPlanEntity().apply {
                    this.modifyType = ModifyTypeEnum.SAVE
                    this.planCode = planName.getCodeFromZh()
                    this.planName = planName
                    this.currency = DEFAULT_CURRENCY
                    this.defaultCommissionRatio = DEFAULT_COMMISSION_RATIO
                }
                plans.add(plan)
            }
            return plan
        }
    }
}