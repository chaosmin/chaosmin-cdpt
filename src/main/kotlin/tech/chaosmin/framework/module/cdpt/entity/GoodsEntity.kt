package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/31 11:52
 */
class GoodsEntity(id: Long? = null) : BaseEntity(id) {
    var departmentId: Long? = null
    var productId: Long? = null
    var productPlanId: String? = null
    var status: Int? = null
    var showStartTime: Date? = null
    var showEndTime: Date? = null
}