package tech.chaosmin.framework.module.cdpt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO

/**
 * 投保单可回溯记录
 *
 * @author Romani min
 * @since 2021/6/7 10:38
 */
class PolicyKhs(id: Long? = null) : BaseDO(id, 0) {
    /** 订单号 */
    var orderNo: String? = null

    /** 保单号 */
    var policyNo: String? = null

    /** 可回溯材料类型 */
    var khsType: String? = null

    /** 资源地址 */
    var resourceUrl: String? = null
}