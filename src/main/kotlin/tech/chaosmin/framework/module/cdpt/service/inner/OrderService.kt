package tech.chaosmin.framework.module.cdpt.service.inner

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Order
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.OrderExt

/**
 * @author Romani min
 * @since 2021/1/26 15:31
 */
interface OrderService : IService<Order> {
    fun findByOrderNo(orderNo: String): Order
    fun pageExt(page: Page<OrderExt>, queryWrapper: Wrapper<OrderExt>): IPage<OrderExt>
}