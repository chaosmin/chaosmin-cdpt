package tech.chaosmin.framework.dao.dataobject

/**
 * 产品计划费率表
 * @author Romani min
 * @since 2020/12/23 11:08
 */
open class ProductPlanRateTable(id: Long? = null) : BaseCommonDO(id, 0) {
    // 计划ID
    var productPlanId: Long? = null

    // 计划code
    var productPlanCode: String? = null

    // 排序
    var sort: Int? = null

    // 类型
    var type: Int? = null

    // 因子
    var factor: String? = null

    // 公式
    var formula: String? = null

    // 保费
    var premium: Double? = null

    // 保费币种
    var premiumCurrency: String? = null
}