package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.RateTableTypeEnum

/**
 * @author Romani min
 * @since 2020/12/26 12:53
 */
class PlanRateTableEntity(id: Long? = null) : BaseEntity(id) {
    var productPlanId: Long? = null
    var productPlanCode: String? = null
    var type: RateTableTypeEnum? = null
    var dayStart: Int? = null
    var dayEnd: Int? = null
    var formula: String? = null
    var premium: Double? = null
    var premiumCurrency: String? = null
    var sort: Int? = null
    var remark: String? = null
}