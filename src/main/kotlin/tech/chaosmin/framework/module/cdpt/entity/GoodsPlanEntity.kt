package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.BasicStatusEnum
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/31 11:52
 */
class GoodsPlanEntity(id: Long? = null) : BaseEntity(id) {
    var departmentName: String? = null
    var roleName: String? = null
    var userId: Long? = null
    var userName: String? = null
    var productPlanId: Long? = null
    var partnerCode: String? = null
    var partnerName: String? = null
    var categoryName: String? = null
    var categorySubName: String? = null
    var productCode: String? = null
    var productName: String? = null
    var productPlanName: String? = null
    var primaryCoverage: String? = null
    var status: BasicStatusEnum? = null
    var isForSale: BasicStatusEnum? = null
    var saleStartTime: Date? = null
    var saleEndTime: Date? = null
    var saleDateScope: List<Date>? = null
    var authorizeTime: Date? = null
    var authorizerId: Long? = null
    var authorizer: String? = null
    var comsRatio: Double? = null
    var maxComsRatio: Double? = null

    /**
     * 返回扩展字段
     */
    var productId: Long? = null

    /*****
     * 新建扩展字段
     *****/
    var userIds: List<Long>? = null
    var plans: Map<Long, Double>? = null
}