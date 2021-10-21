package tech.chaosmin.framework.module.mgmt.domain.dataobject.ext

import tech.chaosmin.framework.module.mgmt.domain.dataobject.User

/**
 * @author Romani min
 * @since 2020/12/25 16:31
 */
class UserExt : User() {
    // 角色集合
    var roles: List<RoleExt>? = null
}