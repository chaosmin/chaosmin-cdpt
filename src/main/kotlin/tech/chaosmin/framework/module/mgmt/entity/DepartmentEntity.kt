package tech.chaosmin.framework.module.mgmt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.module.mgmt.domain.enums.DepartmentStatusEnum

/**
 * @author Romani min
 * @since 2020/12/23 16:56
 */
class DepartmentEntity(id: Long? = null) : BaseEntity(id) {
    var code: String? = null
    var name: String? = null
    var status: DepartmentStatusEnum? = null
    var letterhead: List<LetterheadEntity>? = null
    var numberOfUser: Int? = null
}