package tech.chaosmin.framework.module.cdpt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/31 11:40
 */
open class GoodsPlan(id: Long? = null) : BaseDO(id, 0) {
    var departmentName: String? = null
    var userId: Long? = null
    var userName: String? = null
    var partnerCode: String? = null
    var partnerName: String? = null
    var categoryName: String? = null
    var categorySubName: String? = null
    var productId: Long? = null
    var productCode: String? = null
    var productName: String? = null
    var productPlanId: Long? = null
    var productPlanCode: String? = null
    var productPlanName: String? = null
    var primaryCoverage: String? = null
    var waitingDays: Int? = null
    var clauseUrl: String? = null
    var status: Int? = null
    var forSale: Int? = null
    var saleStartTime: Date? = null
    var saleEndTime: Date? = null
    var authorizeTime: Date? = null
    var authorizerId: Long? = null
    var authorizer: String? = null
    var maxComsRatio: Double? = null
    var comsRatio: Double? = null
}