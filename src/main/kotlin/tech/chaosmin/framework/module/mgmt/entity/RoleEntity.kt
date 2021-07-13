package tech.chaosmin.framework.module.mgmt.entity

import tech.chaosmin.framework.base.BaseEntity

/**
 * 角色信息实体对象 <p>
 * @author Romani min
 * @since 2020/12/23 16:56
 */
class RoleEntity(id: Long? = null) : BaseEntity<RoleEntity>(id) {
    var authorityIds: Set<Long>? = null
    var code: String? = null
    var name: String? = null
    var priority: Int? = null
}