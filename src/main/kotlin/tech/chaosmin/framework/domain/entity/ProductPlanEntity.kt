package tech.chaosmin.framework.domain.entity

import tech.chaosmin.framework.domain.entity.base.BaseEntity
import tech.chaosmin.framework.domain.enums.BasicStatusEnum

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
}