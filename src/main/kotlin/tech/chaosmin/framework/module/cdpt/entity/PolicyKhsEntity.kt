package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.PolicyKhsEnum
import java.util.*

/**
 * @author Romani min
 * @since 2021/6/7 10:49
 */
class PolicyKhsEntity(id: Long? = null) : BaseEntity<PolicyKhsEntity>(id) {
    /** 保单ID */
    var policyId: Long? = null

    /** 可回溯材料类型 */
    var khsType: PolicyKhsEnum? = null

    /** 可回溯材料生成时间 */
    var fileTime: Date? = null

    /** 资源地址 */
    var resourceUrl: String? = null

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

    /** 阅读条款时间 */
    var confirmTime: String? = null

    /** 确认投保时间 */
    var issueTime: String? = null

    /** 阅读须知截图下载地址 */
    var readPicUrl: String? = null

    /** 阅读条款截图下载地址 */
    var confirmPicUrl: String? = null

    /** 确认投保截图下载地址 */
    var issuePicUrl: String? = null
}