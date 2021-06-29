package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.OrderStatusEnum
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/26 15:48
 */
class OrderEntity(id: Long? = null) : BaseEntity(id) {
    var orderNo: String? = null
    var proposalNo: String? = null
    var goodsPlanId: Long? = null
    var startTime: Date? = null
    var endTime: Date? = null
    var travelDestination: String? = null
    var status: OrderStatusEnum? = null
}