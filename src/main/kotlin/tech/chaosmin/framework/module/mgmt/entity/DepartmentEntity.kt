package tech.chaosmin.framework.module.mgmt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.StatusEnum
import tech.chaosmin.framework.module.cdpt.entity.enums.PayTypeEnum

/**
 * 部门信息实体对象 <p>
 * @author Romani min
 * @since 2020/12/23 16:56
 */
class DepartmentEntity(id: Long? = null) : BaseEntity<DepartmentEntity>(id) {
    var code: String? = null
    var name: String? = null
    var status: StatusEnum? = null
    var payType: PayTypeEnum? = null
    var letterHead: List<LetterHeadEntity>? = null
    var numberOfUser: Int? = null
}