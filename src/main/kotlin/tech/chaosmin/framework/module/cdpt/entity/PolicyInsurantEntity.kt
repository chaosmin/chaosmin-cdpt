package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.CertiTypeEnum
import tech.chaosmin.framework.base.enums.CustomerTypeEnum
import tech.chaosmin.framework.base.enums.GenderEnum
import java.util.*

/**
 * 保单被保人信息实体对象 <p>
 * @author Romani min
 * @since 2021/1/26 15:48
 */
class PolicyInsurantEntity(id: Long? = null) : BaseEntity<PolicyInsurantEntity>(id) {
    var birthday: Date? = null
    var certiNo: String? = null
    var certiType: CertiTypeEnum? = null
    var gender: GenderEnum? = null
    var name: String? = null
    var partyType: CustomerTypeEnum? = null
    var phoneNo: String? = null
    var policyId: Long? = null
}