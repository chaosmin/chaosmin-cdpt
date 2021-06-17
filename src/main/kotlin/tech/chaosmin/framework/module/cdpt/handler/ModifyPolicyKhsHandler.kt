package tech.chaosmin.framework.module.cdpt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.PolicyKhsEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.PolicyKhsMapper
import tech.chaosmin.framework.module.cdpt.service.inner.PolicyKhsService

/**
 * @author Romani min
 * @since 2021/6/7 11:12
 */
@Component
open class ModifyPolicyKhsHandler(private val policyKhsService: PolicyKhsService) : AbstractTemplateOperate<PolicyKhsEntity, PolicyKhsEntity>() {
    override fun validation(arg: PolicyKhsEntity, result: RestResult<PolicyKhsEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    override fun processor(arg: PolicyKhsEntity, result: RestResult<PolicyKhsEntity>): RestResult<PolicyKhsEntity> {
        val policyKhs = PolicyKhsMapper.INSTANCE.convert2DO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> policyKhsService.save(policyKhs)
            ModifyTypeEnum.UPDATE -> policyKhsService.updateById(policyKhs)
            ModifyTypeEnum.REMOVE -> policyKhsService.remove(Wrappers.query(policyKhs))
        }
        arg.id = policyKhs.id
        return result.success(arg)
    }
}