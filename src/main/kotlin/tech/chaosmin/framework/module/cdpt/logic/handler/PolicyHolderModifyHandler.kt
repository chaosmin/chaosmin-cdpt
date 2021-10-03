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
import tech.chaosmin.framework.module.cdpt.domain.service.PolicyHolderService
import tech.chaosmin.framework.module.cdpt.entity.PolicyHolderEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.PolicyHolderMapper

/**
 * @author Romani min
 * @since 2021/1/26 15:57
 */
@Component
open class PolicyHolderModifyHandler(private val policyHolderService: PolicyHolderService) :
    AbstractTemplateOperate<PolicyHolderEntity, PolicyHolderEntity>() {
    override fun validation(arg: PolicyHolderEntity, res: RestResult<PolicyHolderEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    override fun processor(arg: PolicyHolderEntity, res: RestResult<PolicyHolderEntity>): RestResult<PolicyHolderEntity> {
        val policyHolder = PolicyHolderMapper.INSTANCE.toDO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> policyHolderService.save(policyHolder)
            ModifyTypeEnum.UPDATE -> policyHolderService.updateById(policyHolder)
            ModifyTypeEnum.REMOVE -> policyHolderService.remove(Wrappers.query(policyHolder))
        }
        return res.success(arg)
    }
}