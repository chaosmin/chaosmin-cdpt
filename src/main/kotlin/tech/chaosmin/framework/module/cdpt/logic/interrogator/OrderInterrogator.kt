/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * OrderInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.interrogator

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Order
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.OrderEx
import tech.chaosmin.framework.module.cdpt.domain.service.OrderService
import tech.chaosmin.framework.module.cdpt.entity.OrderEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.OrderMapper
import tech.chaosmin.framework.module.mgmt.logic.interrogator.UserInterrogator
import tech.chaosmin.framework.utils.SecurityUtil

/**
 * @author Romani min
 * @since 2021/9/4 22:21
 */
@Component
class OrderInterrogator(
    private val userInterrogator: UserInterrogator,
    private val orderDraftInterrogator: OrderDraftInterrogator,
    private val orderService: OrderService
) : Interrogator<OrderEntity, OrderEx> {
    override fun getOne(id: Long): OrderEntity? {
        val order = orderService.getById(id)
        return OrderMapper.INSTANCE.toEn(order)
    }

    fun getByOrderNo(orderNo: String): OrderEntity? {
        val order = orderService.getOne(Wrappers.query<Order?>().eq("order_no", orderNo))
        return OrderMapper.INSTANCE.toEn(order)
    }

    override fun page(cond: PageQuery<OrderEx>): IPage<OrderEntity> {
        var queryWrapper = cond.wrapper
        if (!SecurityUtil.getUserDetails().isAdmin) {
            val subordinate = userInterrogator.findSubordinate().mapNotNull { it.id }.toMutableList()
            subordinate.add(SecurityUtil.getUserId())
            queryWrapper = queryWrapper.`in`("order.user_id", subordinate)
        }
        val page = orderService.pageExt(cond.page, queryWrapper)
        return page.convert(OrderMapper.INSTANCE::exToEn)
    }

    fun loadDraft(orderNo: String): String {
        val draft = orderDraftInterrogator.getOneByOrderNo(orderNo)
        // 如果为空的话, 返回空json串
        return draft?.param ?: "{}"
    }
}