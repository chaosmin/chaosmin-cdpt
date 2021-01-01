package tech.chaosmin.framework.module.cdpt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO
import java.util.*

/**
 * 产品计划佣金配置
 * @author Romani min
 * @since 2020/12/23 11:08
 */
class PlanComs(id: Long? = null) : BaseDO(id, 0) {
    // 计划ID
    var productPlanId: Long? = null

    // 计划code
    var productPlanCode: String? = null

    // 佣金比例
    var commissionRatio: Double? = null

    // 配置状态
    var status: Int? = null

    // 配置时间
    var settleDate: Date? = null
}