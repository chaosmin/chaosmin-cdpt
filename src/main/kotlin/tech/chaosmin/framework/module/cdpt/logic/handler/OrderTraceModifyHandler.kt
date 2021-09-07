/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.service.OrderTraceService
import tech.chaosmin.framework.module.cdpt.entity.OrderTraceEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.OrderTraceMapper

/**
 * @author Romani min
 * @since 2021/6/7 11:12
 */
@Component
open class OrderTraceModifyHandler(private val orderTraceService: OrderTraceService) :
    AbstractTemplateOperate<OrderTraceEntity, OrderTraceEntity>() {
    override fun validation(arg: OrderTraceEntity, res: RestResult<OrderTraceEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    override fun processor(arg: OrderTraceEntity, res: RestResult<OrderTraceEntity>): RestResult<OrderTraceEntity> {
        val policyKhs = OrderTraceMapper.INSTANCE.toDO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> orderTraceService.save(policyKhs)
            ModifyTypeEnum.UPDATE -> orderTraceService.updateById(policyKhs)
            ModifyTypeEnum.REMOVE -> orderTraceService.remove(Wrappers.query(policyKhs))
        }
        arg.id = policyKhs.id
        return res.success(arg)
    }
}