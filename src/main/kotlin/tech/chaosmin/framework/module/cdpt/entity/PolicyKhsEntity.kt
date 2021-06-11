package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.module.cdpt.domain.enums.PolicyKhsEnum
import java.util.*

/**
 * @author Romani min
 * @since 2021/6/7 10:49
 */
class PolicyKhsEntity(id: Long? = null) : BaseEntity(id) {
    /** 保单ID */
    var policyId: Long? = null

    /** 可回溯材料类型 */
    var khsType: PolicyKhsEnum? = null

    /** 可回溯材料生成时间 */
    var fileTime: Date? = null

    /** 资源地址 */
    var resourceUrl: String? = null
}