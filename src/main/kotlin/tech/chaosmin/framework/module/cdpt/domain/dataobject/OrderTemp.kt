package tech.chaosmin.framework.module.cdpt.domain.dataobject

import com.baomidou.mybatisplus.annotation.TableName
import tech.chaosmin.framework.base.BaseDO

/**
 * @author Romani min
 * @since 2021/6/27 20:02
 */
@TableName("`order_temp`")
class OrderTemp(id: Long? = null) : BaseDO(id, 0) {
    /** 订单号 */
    var orderNo: String? = null

    /** 订单参数 */
    var param: String? = null
}