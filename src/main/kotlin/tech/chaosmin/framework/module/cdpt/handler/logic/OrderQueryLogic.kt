package tech.chaosmin.framework.module.cdpt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Order
import tech.chaosmin.framework.module.cdpt.entity.OrderEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.OrderMapper
import tech.chaosmin.framework.module.cdpt.service.inner.OrderService
import tech.chaosmin.framework.module.cdpt.service.inner.OrderTempService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class OrderQueryLogic(
    private val orderService: OrderService,
    private val orderTempService: OrderTempService
) : BaseQueryLogic<OrderEntity, Order> {

    override fun get(id: Long): OrderEntity? {
        val order = orderService.getById(id)
        return OrderMapper.INSTANCE.convert2Entity(order)
    }

    override fun page(cond: PageQuery<Order>): IPage<OrderEntity?> {
        val page = orderService.page(cond.page, cond.wrapper)
        return page.convert(OrderMapper.INSTANCE::convert2Entity)
    }

    fun loadDraft(orderNo: String): String {
        return orderTempService.listByOrderNo(orderNo).firstOrNull()?.param ?: "{}"
    }

    fun saveDraft(orderNo: String, param: String) {
        orderTempService.saveOrUpdate(orderNo, param)
    }
}