package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.module.cdpt.domain.enums.PolicyStatusEnum
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/26 15:53
 */
class PolicyEntity(id: Long? = null) : BaseEntity(id) {
    var orderNo: String? = null
    var proposalNo: String? = null
    var policyNo: String? = null
    var partnerName: String? = null
    var productCode: String? = null
    var productPlanId: Long? = null
    var productPlanCode: String? = null
    var effectiveTime: Date? = null
    var expiryTime: Date? = null
    var travelDestination: String? = null
    var status: PolicyStatusEnum? = null
    var unitPremium: Double? = null
    var totalPremium: Double? = null
    var actualPremium: Double? = null
    var sa: Double? = null
    var totalSa: Double? = null
    var ePolicyUrl: String? = null
    var holder: PolicyHolderEntity? = null
    var insuredList: List<PolicyInsurantEntity>? = null
    var khsList: List<PolicyKhsEntity>? = null
}