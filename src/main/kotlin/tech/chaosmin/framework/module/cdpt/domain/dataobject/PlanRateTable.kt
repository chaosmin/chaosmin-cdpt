package tech.chaosmin.framework.module.cdpt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO

/**
 * 产品计划费率表
 * @author Romani min
 * @since 2020/12/23 11:08
 */
open class PlanRateTable(id: Long? = null) : BaseDO(id, 0) {
    // 计划ID
    var productPlanId: Long? = null

    // 计划code
    var productPlanCode: String? = null

    // 类型
    var type: Int? = null

    // 天数起
    var dayStart: Int? = null

    // 天数止
    var dayEnd: Int? = null

    // 公式
    var formula: String? = null

    // 保费
    var premium: Double? = null

    // 保费币种
    var premiumCurrency: String? = null

    // 排序
    var sort: Int? = null
}
