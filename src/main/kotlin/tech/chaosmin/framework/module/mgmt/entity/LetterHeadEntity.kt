package tech.chaosmin.framework.module.mgmt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.StatusEnum

/**
 * 公司抬头信息实体对象 <p>
 * @author Romani min
 * @since 2021/2/18 16:46
 */
class LetterHeadEntity(id: Long? = null) : BaseEntity<LetterHeadEntity>(id) {
    var departmentId: Long? = null
    var title: String? = null
    var certiNo: String? = null
    var status: StatusEnum? = null
}