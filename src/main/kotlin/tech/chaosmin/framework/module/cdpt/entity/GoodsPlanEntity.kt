package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.StatusEnum
import java.util.*

/**
 * 授权产品信息实体对象 <p>
 * @author Romani min
 * @since 2020/12/31 11:52
 */
class GoodsPlanEntity(id: Long? = null) : BaseEntity<GoodsPlanEntity>(id) {
    var authorizer: String? = null
    var authorizerId: Long? = null
    var authorizeTime: Date? = null
    var categoryName: String? = null
    var categorySubName: String? = null
    var clauseUrl: String? = null
    var comsRatio: Double? = null
    var departmentName: String? = null
    var forSale: StatusEnum? = null
    var insuranceNotice: String? = null
    var liabilities: List<PlanLiabilityEntity>? = null
    var maxComsRatio: Double? = null
    var partnerCode: String? = null
    var partnerName: String? = null
    var plans: Map<Long, Double>? = null
    var primaryCoverage: String? = null
    var productExternal: String? = null
    var productCode: String? = null
    var productId: Long? = null
    var productName: String? = null
    var productPlanCode: String? = null
    var productPlanId: Long? = null
    var productPlanName: String? = null
    var rateTable: List<PlanRateTableEntity>? = null
    var saleDateScope: List<Date>? = null
    var saleStartTime: Date? = null
    var saleEndTime: Date? = null
    var status: StatusEnum? = null
    var userId: Long? = null
    var userIds: List<Long>? = null
    var userName: String? = null
    var waitingDays: Int? = null
}