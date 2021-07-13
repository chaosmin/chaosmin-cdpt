package tech.chaosmin.framework.module.cdpt.service.inner.impl

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.OrderDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Order
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.OrderExt
import tech.chaosmin.framework.module.cdpt.service.inner.OrderService

/**
 * @author Romani min
 * @since 2021/1/26 15:31
 */
@Service
open class OrderServiceImpl : ServiceImpl<OrderDAO, Order>(), OrderService {
    override fun findByOrderNo(orderNo: String): Order {
        val ew = Wrappers.query<Order>().eq("order_no", orderNo)
        return baseMapper.selectList(ew).firstOrNull() ?: Order()
    }

    override fun pageExt(page: Page<OrderExt>, queryWrapper: Wrapper<OrderExt>): IPage<OrderExt> {
        return baseMapper.pageExt(page, queryWrapper)
    }
}