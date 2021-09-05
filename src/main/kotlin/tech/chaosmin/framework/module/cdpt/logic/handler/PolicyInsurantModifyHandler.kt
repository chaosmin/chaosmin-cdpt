/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * ModifyPolicyInsurantHandler.kt
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
import tech.chaosmin.framework.module.cdpt.domain.service.PolicyInsurantService
import tech.chaosmin.framework.module.cdpt.entity.PolicyInsurantEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.PolicyInsurantMapper

/**
 * @author Romani min
 * @since 2021/1/26 15:57
 */
@Component
open class PolicyInsurantModifyHandler(private val policyInsurantService: PolicyInsurantService) :
    AbstractTemplateOperate<PolicyInsurantEntity, PolicyInsurantEntity>() {
    override fun validation(arg: PolicyInsurantEntity, res: RestResult<PolicyInsurantEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    override fun processor(arg: PolicyInsurantEntity, res: RestResult<PolicyInsurantEntity>): RestResult<PolicyInsurantEntity> {
        val policyInsurant = PolicyInsurantMapper.INSTANCE.toDO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> policyInsurantService.save(policyInsurant)
            ModifyTypeEnum.UPDATE -> policyInsurantService.updateById(policyInsurant)
            ModifyTypeEnum.REMOVE -> policyInsurantService.remove(Wrappers.query(policyInsurant))
        }
        return res.success(arg)
    }
}