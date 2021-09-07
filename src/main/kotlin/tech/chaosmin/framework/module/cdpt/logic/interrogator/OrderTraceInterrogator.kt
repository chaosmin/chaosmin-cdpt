/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * OrderTraceInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.interrogator

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.OrderTrace
import tech.chaosmin.framework.module.cdpt.domain.service.OrderTraceService
import tech.chaosmin.framework.module.cdpt.entity.OrderTraceEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.OrderTraceMapper

/**
 * @author Romani min
 * @since 2021/9/6 17:06
 */
@Component
class OrderTraceInterrogator(private val orderTraceService: OrderTraceService) : Interrogator<OrderTraceEntity, OrderTrace> {
    override fun getOne(id: Long): OrderTraceEntity? {
        val orderTrace = orderTraceService.getById(id)
        return OrderTraceMapper.INSTANCE.toEn(orderTrace)
    }

    override fun page(cond: PageQuery<OrderTrace>): IPage<OrderTraceEntity> {
        val page = orderTraceService.page(cond.page, cond.wrapper)
        return page.convert(OrderTraceMapper.INSTANCE::toEn)
    }
}