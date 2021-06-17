package tech.chaosmin.framework.module.cdpt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.PolicyHolderEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.PolicyHolderMapper
import tech.chaosmin.framework.module.cdpt.service.inner.PolicyHolderService

/**
 * @author Romani min
 * @since 2021/1/26 15:57
 */
@Component
open class ModifyPolicyHolderHandler(private val policyHolderService: PolicyHolderService) :
    AbstractTemplateOperate<PolicyHolderEntity, PolicyHolderEntity>() {
    override fun validation(arg: PolicyHolderEntity, result: RestResult<PolicyHolderEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    override fun processor(arg: PolicyHolderEntity, result: RestResult<PolicyHolderEntity>): RestResult<PolicyHolderEntity> {
        val policyHolder = PolicyHolderMapper.INSTANCE.convert2DO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> policyHolderService.save(policyHolder)
            ModifyTypeEnum.UPDATE -> policyHolderService.updateById(policyHolder)
            ModifyTypeEnum.REMOVE -> policyHolderService.remove(Wrappers.query(policyHolder))
        }
        return result.success(arg)
    }
}