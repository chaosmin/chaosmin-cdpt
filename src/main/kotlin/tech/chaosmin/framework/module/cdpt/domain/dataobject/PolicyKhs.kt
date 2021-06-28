package tech.chaosmin.framework.module.cdpt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO
import java.util.*

/**
 * 投保单可回溯记录
 *
 * @author Romani min
 * @since 2021/6/7 10:38
 */
class PolicyKhs(id: Long? = null) : BaseDO(id, 0) {
    /** 保单ID */
    var policyId: Long? = null

    /** 订单号 */
    var orderNo: String? = null

    /** 可回溯材料类型 */
    var khsType: Int? = null

    /** 可回溯材料生成时间 */
    var fileTime: Date? = null

    /** 资源地址 */
    var resourceUrl: String? = null
}