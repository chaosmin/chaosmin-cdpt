package tech.chaosmin.framework.dao.dataobject

/**
 * 产品计划
 * @author Romani min
 * @since 2020/12/23 11:07
 */
open class ProductPlan(id: Long? = null) : BaseCommonDO(id, 0) {
    // 产品ID
    var productId: Long? = null

    // 计划编码
    var planCode: String? = null

    // 计划名称
    var planName: String? = null

    // 主险保额
    var primaryCoverage: String? = null

    // 计划币种
    var currency: String? = null

    // 默认佣金比例
    var defaultCommissionRatio: Double? = null

    // 状态
    var status: Int? = null
}