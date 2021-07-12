package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Order
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.OrderExt
import tech.chaosmin.framework.module.cdpt.entity.OrderEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface OrderMapper {
    companion object {
        val INSTANCE: OrderMapper = Mappers.getMapper(OrderMapper::class.java)
    }

    fun convert2DO(source: OrderEntity?): Order?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: Order?): OrderEntity?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: OrderExt?): OrderEntity?

    fun convert2Entity(source: List<Order>?): List<OrderEntity>?
}