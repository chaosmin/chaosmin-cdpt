package tech.chaosmin.framework.domain.entity

import tech.chaosmin.framework.domain.entity.base.BaseEntity

/**
 * @author Romani min
 * @since 2020/12/26 12:55
 */
class ProductPlanLiabilityEntity(id: Long? = null) : BaseEntity(id) {
    var productPlanId: Long? = null
    var liabilityCategory: String? = null
    var liabilityName: String? = null
    var liabilityRemark: String? = null
    var sort: Int? = null
    var amount: String? = null
    var amountRemark: String? = null
}