package tech.chaosmin.framework.module.cdpt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.GoodsPlanMapper
import tech.chaosmin.framework.module.cdpt.service.GoodsPlanService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyGoodsPlanHandler(private val goodsPlanService: GoodsPlanService) :
    AbstractTemplateOperate<GoodsPlanEntity, GoodsPlanEntity>() {
    override fun validation(arg: GoodsPlanEntity, result: RestResult<GoodsPlanEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType");
        }
    }

    @Transactional
    override fun processor(arg: GoodsPlanEntity, result: RestResult<GoodsPlanEntity>): RestResult<GoodsPlanEntity> {
        val goodsPlan = GoodsPlanMapper.INSTANCE.convert2DO(arg)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> goodsPlanService.save(goodsPlan)
            ModifyTypeEnum.UPDATE -> goodsPlanService.updateById(goodsPlan)
            ModifyTypeEnum.REMOVE -> goodsPlanService.remove(Wrappers.query(goodsPlan))
        }
        return result.success(arg)
    }
}