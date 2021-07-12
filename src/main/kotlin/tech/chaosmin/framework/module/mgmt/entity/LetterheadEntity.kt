package tech.chaosmin.framework.module.mgmt.entity

import tech.chaosmin.framework.base.enums.StatusEnum
import java.io.Serializable

/**
 * 公司抬头信息实体对象 <p>
 * @author Romani min
 * @since 2021/2/18 16:46
 */
class LetterheadEntity : Serializable {
    var certiNo: String? = null
    var departmentId: Long? = null
    var status: StatusEnum? = null
    var title: String? = null
}