package tech.chaosmin.framework.module.mgmt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.StatusEnum

/**
 * @author Romani min
 * @since 2020/12/23 16:56
 */
class DepartmentEntity(id: Long? = null) : BaseEntity(id) {
    var code: String? = null
    var name: String? = null
    var status: StatusEnum? = null
    var letterhead: List<LetterheadEntity>? = null
    var numberOfUser: Int? = null
}