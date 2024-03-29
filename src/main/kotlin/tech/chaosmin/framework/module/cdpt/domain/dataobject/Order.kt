package tech.chaosmin.framework.module.cdpt.domain.dataobject

import com.baomidou.mybatisplus.annotation.TableName
import tech.chaosmin.framework.base.BaseDO

/**
 * @author Romani min
 * @since 2021/1/26 10:35
 */
@TableName("`order`")
open class Order(id: Long? = null) : BaseDO(id, 0) {
    /** 订单号 */
    var orderNo: String? = null

    /** 订单状态 0-暂存 1-已出单 2-废弃 */
    var status: Int? = null

    /** 所属用户id */
    var userId: Long? = null
}