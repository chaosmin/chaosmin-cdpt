package tech.chaosmin.framework.module.mgmt.entity

import tech.chaosmin.framework.base.BaseEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:56
 */
class RoleEntity(id: Long? = null) : BaseEntity(id) {
    var code: String? = null
    var name: String? = null
    var priority: Int? = null
    var authorityIds: Set<Long>? = null
}