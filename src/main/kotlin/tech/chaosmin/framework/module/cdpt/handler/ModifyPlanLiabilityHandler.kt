package tech.chaosmin.framework.module.cdpt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.PlanLiabilityEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.PlanLiabilityMapper
import tech.chaosmin.framework.module.cdpt.service.PlanLiabilityService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyPlanLiabilityHandler(private val productPlanLiabilityService: PlanLiabilityService) :
    AbstractTemplateOperate<PlanLiabilityEntity, PlanLiabilityEntity>() {
    override fun validation(arg: PlanLiabilityEntity, result: RestResult<PlanLiabilityEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType");
        }
    }

    @Transactional
    override fun processor(
        arg: PlanLiabilityEntity,
        result: RestResult<PlanLiabilityEntity>
    ): RestResult<PlanLiabilityEntity> {
        val productPlanLiability = PlanLiabilityMapper.INSTANCE.convert2DO(arg)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> productPlanLiabilityService.save(productPlanLiability)
            ModifyTypeEnum.UPDATE -> productPlanLiabilityService.updateById(productPlanLiability)
            ModifyTypeEnum.REMOVE -> productPlanLiabilityService.remove(Wrappers.query(productPlanLiability))
        }
        return result.success(arg)
    }
}