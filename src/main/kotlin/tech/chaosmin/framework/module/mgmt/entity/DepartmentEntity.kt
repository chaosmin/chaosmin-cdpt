package tech.chaosmin.framework.module.mgmt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.StatusEnum

/**
 * 部门信息实体对象 <p>
 * @author Romani min
 * @since 2020/12/23 16:56
 */
class DepartmentEntity(id: Long? = null) : BaseEntity<DepartmentEntity>(id) {
    var code: String? = null
    var letterhead: List<LetterheadEntity>? = null
    var name: String? = null
    var numberOfUser: Int? = null
    var status: StatusEnum? = null
}