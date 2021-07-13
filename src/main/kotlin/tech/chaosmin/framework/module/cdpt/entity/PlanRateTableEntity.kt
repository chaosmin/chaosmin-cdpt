package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.RateTableTypeEnum

/**
 * 保险计划费率表信息实体对象 <p>
 * @author Romani min
 * @since 2020/12/26 12:53
 */
class PlanRateTableEntity(id: Long? = null) : BaseEntity<PlanRateTableEntity>(id) {
    var dayEnd: Int? = null
    var dayStart: Int? = null
    var formula: String? = null
    var premium: Double? = null
    var premiumCurrency: String? = null
    var productPlanCode: String? = null
    var productPlanId: Long? = null
    var remark: String? = null
    var sort: Int? = null
    var type: RateTableTypeEnum? = null
}