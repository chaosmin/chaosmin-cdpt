package tech.chaosmin.framework.module.cdpt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/26 10:38
 */
class Policy(id: Long? = null) : BaseDO(id, 0) {
    /** 订单号 */
    var orderNo: String? = null

    /** 保单号 */
    var policyNo: String? = null

    /** 产品计划ID */
    var productPlanId: Long? = null

    /** 起保时间 */
    var effectiveTime: Date? = null

    /** 终止时间 */
    var expiryTime: Date? = null

    /** 旅行目的地 */
    var travelDestination: String? = null

    /** 0-待出单 1-出单成功 2-出单失败 */
    var status: Int? = null
}