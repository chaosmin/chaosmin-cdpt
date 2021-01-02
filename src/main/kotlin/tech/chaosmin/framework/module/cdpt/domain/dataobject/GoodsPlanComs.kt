package tech.chaosmin.framework.module.cdpt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO
import java.util.*

/**
 * 产品计划佣金配置
 * @author Romani min
 * @since 2020/12/23 11:08
 */
class GoodsPlanComs(id: Long? = null) : BaseDO(id, 0) {
    // 个人计划ID
    var goodsPlanId: Long? = null

    // 佣金比例
    var comsRatio: Double? = null

    // 配置状态
    var status: Int? = null

    // 配置时间
    var settleDate: Date? = null
}