package tech.chaosmin.framework.module.mgmt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.PayTypeEnum
import tech.chaosmin.framework.base.enums.UserStatusEnum

/**
 * 用户信息实体对象 <p>
 * @author Romani min
 * @since 2020/12/23 15:15
 */
class UserEntity(id: Long? = null) : BaseEntity<UserEntity>(id) {
    var address: String? = null
    var department: String? = null
    var departmentId: Long? = null
    var email: String? = null
    var loginName: String? = null
    var password: String? = null
    var phone: String? = null
    var role: String? = null
    var roleIds: List<Long>? = null
    var status: UserStatusEnum? = null
    var username: String? = null
    var departmentName: String? = null
    var departmentCerti: String? = null
    var payType: PayTypeEnum? = null
}