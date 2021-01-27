package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.CertiTypeEnum
import tech.chaosmin.framework.base.enums.GenderEnum
import tech.chaosmin.framework.module.cdpt.domain.enums.CustomerTypeEnum
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/26 15:48
 */
class PolicyHolderEntity(id: Long? = null) : BaseEntity(id) {
    var orderId: Long? = null
    var policyId: Long? = null
    var mainInsuredRelation: Int? = null
    var partyType: CustomerTypeEnum? = null
    var name: String? = null
    var certiType: CertiTypeEnum? = null
    var certiNo: String? = null
    var birthday: Date? = null
    var gender: GenderEnum? = null
    var phoneNo: String? = null
}