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
    var actualPremium: Double? = null
    var insuredSize: Double? = null
    var effectiveTime: Date? = null
    var expiryTime: Date? = null
    var partnerName: String? = null
    var issueTime: Date? = null
    var issuer: String? = null
    var status: OrderStatusEnum? = null
}