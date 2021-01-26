package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/26 15:48
 */
class PolicyHolderEntity(id: Long? = null) : BaseEntity(id) {
    var orderId: Long? = null
    var policyId: Long? = null
    var mainInsuredRelation: Int? = null
    var partyType: Int? = null
    var name: String? = null
    var certiType: Int? = null
    var certiNo: String? = null
    var birthday: Date? = null
    var gender: Int? = null
    var phoneNo: String? = null
}