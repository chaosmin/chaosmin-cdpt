package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.CertiTypeEnum
import tech.chaosmin.framework.base.enums.CustomerTypeEnum
import tech.chaosmin.framework.base.enums.GenderEnum
import java.util.*

/**
 * 保单投保人中间态实体 <p>
 * <p>
 * @author Romani min
 * @since 2021/1/26 15:48
 */
class PolicyHolderEntity(id: Long? = null) : BaseEntity(id) {
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