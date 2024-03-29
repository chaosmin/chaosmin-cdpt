package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.module.cdpt.entity.enums.OrderStatusEnum
import java.util.*

/**
 * 订单信息实体对象 <p>
 * @author Romani min
 * @since 2021/1/26 15:48
 */
class OrderEntity(id: Long? = null) : BaseEntity<OrderEntity>(id) {
    var actualPremium: Double? = null
    var effectiveTime: Date? = null
    var expiryTime: Date? = null
    var goodsPlanId: Long? = null
    var insuredSize: Double? = null
    var issuerName: String? = null
    var issueTime: Date? = null
    var orderNo: String? = null
    var policyNo: String? = null
    var proposalNo: String? = null
    var productName: String? = null
    var partnerName: String? = null
    var status: OrderStatusEnum? = null
    var userId: Long? = null
    var param: String? = null
}