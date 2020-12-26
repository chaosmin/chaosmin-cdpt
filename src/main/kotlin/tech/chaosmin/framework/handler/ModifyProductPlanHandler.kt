package tech.chaosmin.framework.handler

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.convert.ProductPlanLiabilityMapper
import tech.chaosmin.framework.dao.convert.ProductPlanMapper
import tech.chaosmin.framework.dao.convert.ProductPlanRateTableMapper
import tech.chaosmin.framework.dao.dataobject.ProductPlanLiability
import tech.chaosmin.framework.dao.dataobject.ProductPlanRateTable
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.entity.ProductPlanEntity
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.handler.base.AbstractTemplateOperate
import tech.chaosmin.framework.service.ProductPlanLiabilityService
import tech.chaosmin.framework.service.ProductPlanRateTableService
import tech.chaosmin.framework.service.ProductPlanService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyProductPlanHandler(
    private val productPlanService: ProductPlanService,
    private val productPlanLiabilityService: ProductPlanLiabilityService,
    private val productPlanRateTableService: ProductPlanRateTableService
) : AbstractTemplateOperate<ProductPlanEntity, ProductPlanEntity>() {
    private val pid = "product_plan_id"

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
            ModifyTypeEnum.SAVE -> {
                productPlanService.save(productPlan)
                val liabilities = arg.liabilities.mapIndexed { index, it ->
                    ProductPlanLiabilityMapper.INSTANCE.convert2DO(it).apply {
                        this.productPlanId = productPlan.id
                        this.sort = index + 1
                    }
                }
                productPlanLiabilityService.saveBatch(liabilities)
                val rateTable = arg.rateTable.mapIndexed { index, it ->
                    ProductPlanRateTableMapper.INSTANCE.convert2DO(it).apply {
                        this.productPlanId = productPlan.id
                        this.sort = index + 1
                    }
                }
                productPlanRateTableService.saveBatch(rateTable)
            }
            ModifyTypeEnum.UPDATE -> {
                productPlanService.updateById(productPlan)
                val liabilities = arg.liabilities.map { ProductPlanLiabilityMapper.INSTANCE.convert2DO(it) }
                productPlanLiabilityService.updateBatchById(liabilities)
                val rateTable = arg.rateTable.map { ProductPlanRateTableMapper.INSTANCE.convert2DO(it) }
                productPlanRateTableService.updateBatchById(rateTable)
            }
            ModifyTypeEnum.REMOVE -> {
                productPlanService.remove(Wrappers.query(productPlan))
                productPlanLiabilityService.remove(QueryWrapper<ProductPlanLiability>().eq(pid, productPlan.id))
                productPlanRateTableService.remove(QueryWrapper<ProductPlanRateTable>().eq(pid, productPlan.id))
            }
        }
        return result.success(ProductPlanMapper.INSTANCE.convert2Entity(productPlan))
    }
}