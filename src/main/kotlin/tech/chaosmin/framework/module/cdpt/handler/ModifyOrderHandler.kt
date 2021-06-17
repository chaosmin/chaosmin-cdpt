package tech.chaosmin.framework.module.cdpt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.OrderEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.OrderMapper
import tech.chaosmin.framework.module.cdpt.service.inner.OrderService

/**
 * @author Romani min
 * @since 2021/1/26 15:57
 */
@Component
open class ModifyOrderHandler(private val orderService: OrderService) : AbstractTemplateOperate<OrderEntity, OrderEntity>() {
    override fun validation(arg: OrderEntity, result: RestResult<OrderEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    override fun processor(arg: OrderEntity, result: RestResult<OrderEntity>): RestResult<OrderEntity> {
        val order = OrderMapper.INSTANCE.convert2DO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> orderService.save(order)
            ModifyTypeEnum.UPDATE -> orderService.updateById(order)
            ModifyTypeEnum.REMOVE -> orderService.remove(Wrappers.query(order))
        }
        arg.id = order.id
        return result.success(arg)
    }
}