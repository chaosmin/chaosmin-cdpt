package tech.chaosmin.framework.module.cdpt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.PolicyInsurantEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.PolicyInsurantMapper
import tech.chaosmin.framework.module.cdpt.service.PolicyInsurantService

/**
 * @author Romani min
 * @since 2021/1/26 15:57
 */
@Component
open class ModifyPolicyInsurantHandler(private val policyInsurantService: PolicyInsurantService) :
    AbstractTemplateOperate<PolicyInsurantEntity, PolicyInsurantEntity>() {
    override fun validation(arg: PolicyInsurantEntity, result: RestResult<PolicyInsurantEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    override fun processor(arg: PolicyInsurantEntity, result: RestResult<PolicyInsurantEntity>): RestResult<PolicyInsurantEntity> {
        val policyInsurant = PolicyInsurantMapper.INSTANCE.convert2DO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> policyInsurantService.save(policyInsurant)
            ModifyTypeEnum.UPDATE -> policyInsurantService.updateById(policyInsurant)
            ModifyTypeEnum.REMOVE -> policyInsurantService.remove(Wrappers.query(policyInsurant))
        }
        return result.success(arg)
    }
}