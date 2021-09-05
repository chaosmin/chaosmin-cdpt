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
import tech.chaosmin.framework.module.cdpt.domain.service.PolicyTraceService
import tech.chaosmin.framework.module.cdpt.entity.PolicyTraceEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.PolicyTraceMapper

/**
 * @author Romani min
 * @since 2021/6/7 11:12
 */
@Component
open class PolicyTraceModifyHandler(private val policyTraceService: PolicyTraceService) :
    AbstractTemplateOperate<PolicyTraceEntity, PolicyTraceEntity>() {
    override fun validation(arg: PolicyTraceEntity, res: RestResult<PolicyTraceEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    override fun processor(arg: PolicyTraceEntity, res: RestResult<PolicyTraceEntity>): RestResult<PolicyTraceEntity> {
        val policyKhs = PolicyTraceMapper.INSTANCE.toDO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> policyTraceService.save(policyKhs)
            ModifyTypeEnum.UPDATE -> policyTraceService.updateById(policyKhs)
            ModifyTypeEnum.REMOVE -> policyTraceService.remove(Wrappers.query(policyKhs))
        }
        arg.id = policyKhs.id
        return res.success(arg)
    }
}