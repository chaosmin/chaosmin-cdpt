package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import java.math.BigDecimal

/**
 * @author Romani min
 * @since 2021/6/17 18:57
 */
class PolicyCoverage {
    // 责任代码
    var kindCode: String? = null

    // 关联责任代码
    var relatedCoverageCode: String? = null

    // 单位保费
    var unitPremium: BigDecimal? = null

    // 费率
    var premiumRate: BigDecimal? = null

    // 应收保费/真实保费
    var duePremium: BigDecimal? = null

    // 平均保额
    var avgSumInsured: BigDecimal? = null

    // 合计保额
    var sumInsured: BigDecimal? = null

    // 条款代码
    var clauseCode: String? = null

    // 补贴金额
    var subsidyAmount: BigDecimal? = null

    // 补贴天数
    var subsidyDay: Long? = null

    // 补贴类型
    var subsidyType: String? = null

    // 条款或责任层面的特别约定列表(formCategory为2)
    var policyFormList: List<PolicyForm>? = null

    // 限额免赔列表
    var limitDeductibleList: List<LimitDeductible>? = null
}