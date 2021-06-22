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

    /** 投保单号 */
    var proposalNo: String? = null

    /** 保单号 */
    var policyNo: String? = null

    /** 承保公司 */
    var partnerName: String? = null

    /** 产品Code */
    var productCode: String? = null

    /** 产品计划ID */
    var productPlanId: Long? = null

    /** 产品计划Code */
    var productPlanCode: String? = null

    /** 起保时间 */
    var effectiveTime: Date? = null

    /** 终止时间 */
    var expiryTime: Date? = null

    /** 旅行目的地 */
    var travelDestination: String? = null

    /** 0-待出单 1-出单成功 2-出单失败 3-出单中 */
    var status: Int? = null

    /** 单价保费 */
    var unitPremium: Double? = null

    /** 原总保费 */
    var totalPremium: Double? = null

    /** 实际保费 */
    var actualPremium: Double? = null

    /** 单人保额 */
    var sa: Double? = null

    /** 总保额 */
    var totalSa: Double? = null

    /** 电子保单下载地址 */
    var ePolicyUrl: String? = null
}