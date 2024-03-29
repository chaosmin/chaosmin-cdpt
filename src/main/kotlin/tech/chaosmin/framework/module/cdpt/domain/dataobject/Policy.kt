package tech.chaosmin.framework.module.cdpt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/26 10:38
 */
open class Policy(id: Long? = null) : BaseDO(id, 0) {
    /** 订单号 */
    var orderNo: String? = null

    /** 投保单号 */
    var proposalNo: String? = null

    /** 保单号 */
    var policyNo: String? = null

    /** 用户ID */
    var userId: Long? = null

    /** 产品授权ID */
    var goodsPlanId: Long? = null

    /** 出单时间 */
    var orderTime: Date? = null

    /** 承保时间 */
    var issueTime: Date? = null

    /** 起保时间 */
    var effectiveTime: Date? = null

    /** 终止时间 */
    var expiryTime: Date? = null

    /** 旅行目的地 */
    var travelDestination: String? = null

    /** 团单号 */
    var groupNo: String? = null

    /** 被保人数 */
    var insuredSize: Int? = null

    /** 保单状态 */
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

    /** 支付状态 */
    var payStatus: Int? = null

    /** 支付方式 */
    var payType: Int? = null

    /** 退保时间 */
    var cancelTime: Date? = null

    /** 退费时间 */
    var refundTime: Date? = null
}