/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * OrderMapper.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.IMapper
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Order
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.OrderEx
import tech.chaosmin.framework.module.cdpt.entity.OrderEntity

@Mapper(uses = [KeyValueEnumMapper::class])
interface OrderMapper : IMapper<OrderEntity, Order> {
    companion object {
        val INSTANCE: OrderMapper = Mappers.getMapper(OrderMapper::class.java)
    }

    @Mapping(target = "modifyType", ignore = true)
    fun exToEn(source: OrderEx?): OrderEntity?
}