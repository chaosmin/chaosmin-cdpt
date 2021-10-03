/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * OrderDraftMapper.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.IMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.OrderDraft
import tech.chaosmin.framework.module.cdpt.entity.OrderDraftEntity

/**
 * @author Romani min
 * @since 2021/9/6 17:12
 */
@Mapper
interface OrderDraftMapper : IMapper<OrderDraftEntity, OrderDraft> {
    companion object {
        val INSTANCE: OrderDraftMapper = Mappers.getMapper(OrderDraftMapper::class.java)
    }
}