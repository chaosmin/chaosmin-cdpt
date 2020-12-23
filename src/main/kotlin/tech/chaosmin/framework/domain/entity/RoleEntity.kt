package tech.chaosmin.framework.domain.entity

import tech.chaosmin.framework.domain.entity.base.BaseEntity

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