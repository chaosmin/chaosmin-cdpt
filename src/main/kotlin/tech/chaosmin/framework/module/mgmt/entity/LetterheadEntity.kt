package tech.chaosmin.framework.module.mgmt.entity

import tech.chaosmin.framework.base.enums.BasicStatusEnum
import java.io.Serializable

/**
 * @author Romani min
 * @since 2021/2/18 16:46
 */
class LetterheadEntity : Serializable {
    var departmentId: Long? = null
    var title: String? = null
    var certiNo: String? = null
    var status: BasicStatusEnum? = null
}