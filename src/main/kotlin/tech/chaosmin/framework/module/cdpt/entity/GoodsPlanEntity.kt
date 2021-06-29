package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.StatusEnum
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/31 11:52
 */
class GoodsPlanEntity(id: Long? = null) : BaseEntity(id) {
    // 部门名称
    var departmentName: String? = null

    // 用户ID
    var userId: Long? = null

    // 用户名称
    var userName: String? = null

    // 保险公司编号
    var partnerCode: String? = null

    // 保险公司名称
    var partnerName: String? = null

    // 产品大类名称
    var categoryName: String? = null

    // 产品分类名称
    var categorySubName: String? = null

    // 产品ID
    var productId: Long? = null

    // 产品编号
    var productCode: String? = null

    // 产品名称
    var productName: String? = null

    // 计划ID
    var productPlanId: Long? = null

    // 产品计划编号
    var productPlanCode: String? = null

    // 产品计划名称
    var productPlanName: String? = null

    // 主要责任保额
    var primaryCoverage: String? = null

    // 授权状态
    var status: StatusEnum? = null

    // 是否可售
    var forSale: StatusEnum? = null

    // 可售开始时间
    var saleStartTime: Date? = null

    // 可售结束时间
    var saleEndTime: Date? = null

    // 授权时间
    var authorizeTime: Date? = null

    // 授权人ID
    var authorizerId: Long? = null

    // 授权人
    var authorizer: String? = null

    // 产品等待期
    var waitingDays: Int? = null

    // 授权佣金比例
    var comsRatio: Double? = null

    // 产品最大佣金比例
    var maxComsRatio: Double? = null

    /** 条款下载地址 */
    var clauseUrl: String? = null

    // 计划责任
    var liabilities: List<PlanLiabilityEntity>? = null

    // 计划费率表
    var rateTable: List<PlanRateTableEntity>? = null

    // 投保提示
    var insuranceNotice: String? = null

    // 产品须知
    var productExternal: String? = null

    /*****
     * 产品授权功能 扩展字段
     *****/
    var userIds: List<Long>? = null
    var plans: Map<Long, Double>? = null
    var saleDateScope: List<Date>? = null
}