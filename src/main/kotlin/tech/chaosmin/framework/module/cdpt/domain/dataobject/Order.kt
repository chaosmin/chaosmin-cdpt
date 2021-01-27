package tech.chaosmin.framework.module.cdpt.domain.dataobject

import com.baomidou.mybatisplus.annotation.TableName
import tech.chaosmin.framework.base.BaseDO
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/26 10:35
 */
@TableName("`order`")
class Order(id: Long? = null) : BaseDO(id, 0) {
    /** 订单号 */
    var orderNo: String? = null

    /** 产品计划ID */
    var productPlanId: Long? = null

    /** 起保时间 */
    var startTime: Date? = null

    /** 终止时间 */
    var endTime: Date? = null

    /** 旅行目的地 */
    var travelDestination: String? = null

    /** 订单状态 0-暂存 1-已出单 2-废弃 */
    var status: Int? = null
}