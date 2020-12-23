package tech.chaosmin.framework.domain.entity

import tech.chaosmin.framework.domain.entity.base.BaseEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:56
 */
class DepartmentEntity(id: Long? = null) : BaseEntity(id) {
    var code: String? = null
    var name: String? = null
    var status: Int? = null

    var numberOfPeople: Int? = null
}