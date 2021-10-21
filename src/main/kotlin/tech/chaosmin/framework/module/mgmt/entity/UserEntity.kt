package tech.chaosmin.framework.module.mgmt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.UserStatusEnum
import tech.chaosmin.framework.module.cdpt.entity.enums.PayTypeEnum

/**
 * 用户信息实体对象 <p>
 * @author Romani min
 * @since 2020/12/23 15:15
 */
class UserEntity(id: Long? = null) : BaseEntity<UserEntity>(id) {
    var address: String? = null
    var email: String? = null
    var loginName: String? = null
    var password: String? = null
    var phone: String? = null
    var role: String? = null
    var roleIds: List<Long>? = null
    var status: UserStatusEnum? = null
    var username: String? = null
    var payType: PayTypeEnum? = null
    var letterHead: List<LetterHeadEntity>? = null
}