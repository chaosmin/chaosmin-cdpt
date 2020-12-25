package tech.chaosmin.framework.domain.entity

import tech.chaosmin.framework.domain.entity.base.BaseEntity
import tech.chaosmin.framework.domain.enums.DepartmentStatusEnum

/**
 * @author Romani min
 * @since 2020/12/23 16:56
 */
class DepartmentEntity(id: Long? = null) : BaseEntity(id) {
    var code: String? = null
    var name: String? = null
    var status: DepartmentStatusEnum? = null
    var numberOfUser: Int? = null
}