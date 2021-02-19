package tech.chaosmin.framework.module.mgmt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.module.mgmt.domain.enums.UserStatusEnum

/**
 * @author Romani min
 * @since 2020/12/23 15:15
 */
class UserEntity(id: Long? = null) : BaseEntity(id) {
    var departmentId: Long? = null
    var username: String? = null
    var loginName: String? = null
    var password: String? = null
    var status: UserStatusEnum? = null
    var phone: String? = null
    var email: String? = null
    var address: String? = null
    var roleIds: List<Long>? = null

    var department: String? = null
    var role: String? = null
}