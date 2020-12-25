package tech.chaosmin.framework.dao.dataobject.ext

import tech.chaosmin.framework.dao.dataobject.User

/**
 * @author Romani min
 * @since 2020/12/25 16:31
 */
class UserExt : User() {
    // 部门名称
    var department: String? = null

    // 角色ID
    var roleId: Long? = null

    // 角色
    var role: String? = null
}