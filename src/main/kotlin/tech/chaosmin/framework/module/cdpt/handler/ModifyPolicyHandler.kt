package tech.chaosmin.framework.module.cdpt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.PolicyMapper
import tech.chaosmin.framework.module.cdpt.service.inner.PolicyService

/**
 * @author Romani min
 * @since 2021/1/26 15:57
 */
@Component
open class ModifyPolicyHandler(private val policyService: PolicyService) : AbstractTemplateOperate<PolicyEntity, PolicyEntity>() {
    override fun validation(arg: PolicyEntity, result: RestResult<PolicyEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    override fun processor(arg: PolicyEntity, result: RestResult<PolicyEntity>): RestResult<PolicyEntity> {
        val policy = PolicyMapper.INSTANCE.convert2DO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> policyService.save(policy)
            ModifyTypeEnum.UPDATE -> policyService.updateById(policy)
            ModifyTypeEnum.REMOVE -> policyService.remove(Wrappers.query(policy))
        }
        arg.id = policy.id
        return result.success(arg)
    }
}