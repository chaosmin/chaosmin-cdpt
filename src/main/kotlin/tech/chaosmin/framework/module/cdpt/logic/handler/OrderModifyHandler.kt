/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.service.OrderDraftService
import tech.chaosmin.framework.module.cdpt.domain.service.OrderService
import tech.chaosmin.framework.module.cdpt.entity.OrderEntity
import tech.chaosmin.framework.module.cdpt.entity.enums.OrderStatusEnum
import tech.chaosmin.framework.module.cdpt.logic.convert.OrderMapper
import tech.chaosmin.framework.utils.SecurityUtil

/**
 * @author Romani min
 * @since 2021/1/26 15:57
 */
@Component
open class OrderModifyHandler(
    private val orderService: OrderService,
    private val orderDraftService: OrderDraftService
) : AbstractTemplateOperate<OrderEntity, OrderEntity>() {
    private val logger = LoggerFactory.getLogger(OrderModifyHandler::class.java)

    override fun validation(arg: OrderEntity, res: RestResult<OrderEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    override fun processor(arg: OrderEntity, res: RestResult<OrderEntity>): RestResult<OrderEntity> {
        val order = OrderMapper.INSTANCE.toDO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> {
                logger.info("order.create.input: $order")
                orderService.save(order)
                orderDraftService.saveOrUpdate(order.orderNo!!, arg.param ?: "{}")
            }
            ModifyTypeEnum.UPDATE -> {
                logger.info("order.update.input: $order")
                orderService.updateById(order)
                orderDraftService.saveOrUpdate(order.orderNo!!, arg.param ?: "{}")
            }
            ModifyTypeEnum.REMOVE -> {
                logger.info("order.remove.input: $order")
                orderService.remove(Wrappers.query(order))
            }
        }
        arg.id = order.id
        return res.success(arg)
    }

    fun saveOrUpdate(orderNo: String, param: String): RestResult<OrderEntity> {
        val orderEntity = OrderEntity().apply {
            this.userId = SecurityUtil.getUserId()
            this.status = OrderStatusEnum.DRAFT
            this.orderNo = orderNo
            this.param = param
        }
        val id = orderService.findByOrderNo(orderNo).id
        return if (id == null) operate(orderEntity.save())
        else operate(orderEntity.update(id))
    }
}