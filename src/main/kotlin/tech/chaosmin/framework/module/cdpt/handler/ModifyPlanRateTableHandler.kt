package tech.chaosmin.framework.module.cdpt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.PlanRateTableEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.PlanRateTableMapper
import tech.chaosmin.framework.module.cdpt.service.PlanRateTableService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyPlanRateTableHandler(private val planRateTableService: PlanRateTableService) :
    AbstractTemplateOperate<PlanRateTableEntity, PlanRateTableEntity>() {
    override fun validation(arg: PlanRateTableEntity, result: RestResult<PlanRateTableEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    @Transactional
    override fun processor(
        arg: PlanRateTableEntity,
        result: RestResult<PlanRateTableEntity>
    ): RestResult<PlanRateTableEntity> {
        val planRateTable = PlanRateTableMapper.INSTANCE.convert2DO(arg)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> planRateTableService.save(planRateTable)
            ModifyTypeEnum.UPDATE -> planRateTableService.updateById(planRateTable)
            ModifyTypeEnum.REMOVE -> planRateTableService.remove(Wrappers.query(planRateTable))
        }
        return result.success(arg)
    }
}