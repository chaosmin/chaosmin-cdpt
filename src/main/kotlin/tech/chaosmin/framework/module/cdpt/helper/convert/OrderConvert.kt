package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.cdpt.entity.OrderEntity
import tech.chaosmin.framework.module.cdpt.entity.request.OrderReq
import tech.chaosmin.framework.module.cdpt.entity.response.OrderResp

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper
interface OrderConvert : BaseConvert<OrderEntity, OrderReq, OrderResp> {
    companion object {
        val INSTANCE: OrderConvert = Mappers.getMapper(OrderConvert::class.java)
    }
}