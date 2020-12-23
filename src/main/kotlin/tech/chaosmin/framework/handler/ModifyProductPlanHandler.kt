package tech.chaosmin.framework.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.convert.ProductPlanMapper
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.entity.ProductPlanEntity
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.handler.base.AbstractTemplateOperate
import tech.chaosmin.framework.service.ProductPlanService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyProductPlanHandler(private val productPlanService: ProductPlanService) :
    AbstractTemplateOperate<ProductPlanEntity, ProductPlanEntity>() {
    override fun validation(arg: ProductPlanEntity, result: RestResult<ProductPlanEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType");
        }
    }

    @Transactional
    override fun processor(
        arg: ProductPlanEntity,
        result: RestResult<ProductPlanEntity>
    ): RestResult<ProductPlanEntity> {
        val productPlan = ProductPlanMapper.INSTANCE.convert2DO(arg)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> productPlanService.save(productPlan)
            ModifyTypeEnum.UPDATE -> productPlanService.updateById(productPlan)
            ModifyTypeEnum.REMOVE -> productPlanService.remove(Wrappers.query(productPlan))
        }
        return result.success(arg)
    }
}