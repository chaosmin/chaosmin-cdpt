package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.PayTypeEnum
import tech.chaosmin.framework.base.enums.PolicyStatusEnum
import java.util.*

/**
 * 保单信息实体对象 <p>
 * @author Romani min
 * @since 2021/1/26 15:53
 */
class PolicyEntity(id: Long? = null) : BaseEntity<PolicyEntity>(id) {
    var actualPremium: Double? = null
    var effectiveTime: Date? = null
    var ePolicyUrl: String? = null
    var expiryTime: Date? = null
    var goodsPlan: GoodsPlanEntity? = null
    var goodsPlanId: Long? = null
    var groupNo: String? = null
    var holder: PolicyHolderEntity? = null
    var insuredList: List<PolicyInsurantEntity>? = null
    var insuredSize: Int? = null
    var khsList: List<PolicyKhsEntity>? = null
    var orderNo: String? = null
    var proposalNo: String? = null
    var policyNo: String? = null
    var sa: Double? = null
    var status: PolicyStatusEnum? = null
    var totalPremium: Double? = null
    var totalSa: Double? = null
    var travelDestination: String? = null
    var unitPremium: Double? = null
    var userId: Long? = null
    var payType: PayTypeEnum? = null
}