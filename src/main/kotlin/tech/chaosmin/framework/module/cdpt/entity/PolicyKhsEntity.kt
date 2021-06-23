package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity

/**
 * @author Romani min
 * @since 2021/6/7 10:49
 */
class PolicyKhsEntity(id: Long? = null) : BaseEntity(id) {
    /** 保单ID */
    var policyId: Long? = null

    /** 订单号 */
    var orderNo: String? = null

    /** 保单号 */
    var policyNo: String? = null

    /** 投保人名称 */
    var holderName: String? = null

    /** 出单人名称 */
    var issuerName: String? = null

    /** 进入页面时间 */
    var enterPageTime: String? = null

    /** 离开页面时间 */
    var leavePageTime: String? = null

    /** 阅读须知时间 */
    var readTime: String? = null

    /** 确认投保时间 */
    var issueTime: String? = null

    /** 阅读须知截图下载地址 */
    var readPicUrl: String? = null

    /** 确认投保截图下载地址 */
    var issuePicUrl: String? = null
}