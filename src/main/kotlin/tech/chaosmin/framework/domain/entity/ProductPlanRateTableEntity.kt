package tech.chaosmin.framework.domain.entity

import tech.chaosmin.framework.domain.entity.base.BaseEntity
import tech.chaosmin.framework.domain.enums.RateTableTypeEnum

/**
 * @author Romani min
 * @since 2020/12/26 12:53
 */
class ProductPlanRateTableEntity(id: Long? = null) : BaseEntity(id) {
    var productPlanId: Long? = null
    var productPlanCode: String? = null
    var type: RateTableTypeEnum? = null
    var dayStart: Int? = null
    var dayEnd: Int? = null
    var formula: String? = null
    var premium: Double? = null
    var premiumCurrency: String? = null
    var sort: Int? = null
}