package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity

/**
 * @author Romani min
 * @since 2020/12/26 12:55
 */
class PlanLiabilityEntity(id: Long? = null) : BaseEntity(id) {
    var productPlanId: Long? = null
    var productPlanCode: String? = null
    var planCode: String? = null
    var liabilityCategory: String? = null
    var liabilityName: String? = null
    var liabilityRemark: String? = null
    var sort: Int? = null
    var amount: String? = null
    var amountRemark: String? = null
}