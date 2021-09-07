/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * OrderDraftInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.interrogator

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.OrderDraft
import tech.chaosmin.framework.module.cdpt.domain.service.OrderDraftService
import tech.chaosmin.framework.module.cdpt.entity.OrderDraftEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.OrderDraftMapper

/**
 * @author Romani min
 * @since 2021/9/6 17:09
 */
@Component
class OrderDraftInterrogator(private val orderDraftService: OrderDraftService) : Interrogator<OrderDraftEntity, OrderDraft> {
    override fun getOne(id: Long): OrderDraftEntity? {
        val orderDraft = orderDraftService.getById(id)
        return OrderDraftMapper.INSTANCE.toEn(orderDraft)
    }

    fun getOneByOrderNo(orderNo: String): OrderDraftEntity? {
        val orderDraft = orderDraftService.listByOrderNo(orderNo).firstOrNull()
        return OrderDraftMapper.INSTANCE.toEn(orderDraft)
    }

    override fun page(cond: PageQuery<OrderDraft>): IPage<OrderDraftEntity> {
        val page = orderDraftService.page(cond.page, cond.wrapper)
        return page.convert(OrderDraftMapper.INSTANCE::toEn)
    }
}