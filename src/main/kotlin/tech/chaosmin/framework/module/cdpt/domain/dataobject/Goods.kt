package tech.chaosmin.framework.module.cdpt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/31 11:39
 */
class Goods(id: Long? = null) : BaseDO(id, 0) {
    // 机构ID
    var departmentId: Long? = null

    // 产品ID
    var productId: Long? = null

    // 可用计划ID集合
    var productPlanId: String? = null

    // 商品状态
    var status: Int? = null

    // 展示开始时间
    var showStartTime: Date? = null

    // 展示结束时间
    var showEndTime: Date? = null
}