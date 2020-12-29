package tech.chaosmin.framework.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.convert.ProductPlanRateTableMapper
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.entity.ProductPlanRateTableEntity
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.handler.base.AbstractTemplateOperate
import tech.chaosmin.framework.service.ProductPlanRateTableService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyProductPlanRateTableHandler(private val productPlanRateTableService: ProductPlanRateTableService) :
    AbstractTemplateOperate<ProductPlanRateTableEntity, ProductPlanRateTableEntity>() {
    override fun validation(arg: ProductPlanRateTableEntity, result: RestResult<ProductPlanRateTableEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType");
        }
    }

    @Transactional
    override fun processor(
        arg: ProductPlanRateTableEntity,
        result: RestResult<ProductPlanRateTableEntity>
    ): RestResult<ProductPlanRateTableEntity> {
        val productPlanRateTable = ProductPlanRateTableMapper.INSTANCE.convert2DO(arg)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> productPlanRateTableService.save(productPlanRateTable)
            ModifyTypeEnum.UPDATE -> productPlanRateTableService.updateById(productPlanRateTable)
            ModifyTypeEnum.REMOVE -> productPlanRateTableService.remove(Wrappers.query(productPlanRateTable))
        }
        return result.success(arg)
    }
}