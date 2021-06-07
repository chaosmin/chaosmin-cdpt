package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.module.cdpt.domain.enums.PolicyKhsEnum

/**
 * @author Romani min
 * @since 2021/6/7 10:49
 */
class PolicyKhsEntity(id: Long? = null) : BaseEntity(id) {
    /** 订单号 */
    var orderNo: String? = null

    /** 保单号 */
    var policyNo: String? = null

    /** 可回溯材料类型 */
    var khsType: PolicyKhsEnum? = null

    /** 资源地址 */
    var resourceUrl: String? = null
}