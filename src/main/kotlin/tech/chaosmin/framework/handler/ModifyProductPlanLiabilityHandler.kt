package tech.chaosmin.framework.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.convert.ProductPlanLiabilityMapper
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.entity.ProductPlanLiabilityEntity
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.handler.base.AbstractTemplateOperate
import tech.chaosmin.framework.service.ProductPlanLiabilityService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyProductPlanLiabilityHandler(private val productPlanLiabilityService: ProductPlanLiabilityService) :
    AbstractTemplateOperate<ProductPlanLiabilityEntity, ProductPlanLiabilityEntity>() {
    override fun validation(arg: ProductPlanLiabilityEntity, result: RestResult<ProductPlanLiabilityEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType");
        }
    }

    @Transactional
    override fun processor(
        arg: ProductPlanLiabilityEntity,
        result: RestResult<ProductPlanLiabilityEntity>
    ): RestResult<ProductPlanLiabilityEntity> {
        val productPlanLiability = ProductPlanLiabilityMapper.INSTANCE.convert2DO(arg)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> productPlanLiabilityService.save(productPlanLiability)
            ModifyTypeEnum.UPDATE -> productPlanLiabilityService.updateById(productPlanLiability)
            ModifyTypeEnum.REMOVE -> productPlanLiabilityService.remove(Wrappers.query(productPlanLiability))
        }
        return result.success(arg)
    }
}