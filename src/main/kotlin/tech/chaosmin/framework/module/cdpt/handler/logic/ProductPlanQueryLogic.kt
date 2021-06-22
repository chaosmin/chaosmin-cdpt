package tech.chaosmin.framework.module.cdpt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductPlanExt
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.ProductPlanMapper
import tech.chaosmin.framework.module.cdpt.service.inner.GoodsPlanService
import tech.chaosmin.framework.module.cdpt.service.inner.ProductPlanService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class ProductPlanQueryLogic(
    private val productPlanService: ProductPlanService,
    private val goodsPlanService: GoodsPlanService
) : BaseQueryLogic<ProductPlanEntity, ProductPlanExt> {

    override fun get(id: Long): ProductPlanEntity? {
        val productPlan = productPlanService.getById(id)
        return ProductPlanMapper.INSTANCE.convert2Entity(productPlan)
    }

    override fun page(cond: PageQuery<ProductPlanExt>): IPage<ProductPlanEntity?> {
        val page = productPlanService.pageExt(cond.page, cond.wrapper)
        return page.convert(ProductPlanMapper.INSTANCE::convert2Entity)
    }

    fun contract(userId: Long): IPage<ProductPlanEntity?> {
        val contactPlans = goodsPlanService.getByUser(userId)
        if (contactPlans.isEmpty()) {
            return Page<ProductPlanEntity>()
        }
        val ew = PageQuery.inQuery<ProductPlanExt>("product_plan.id", contactPlans.mapNotNull { it.productPlanId })
        val result = this.page(ew)
        // 更新一下可用的授权佣金比例
        result.records.forEach { record ->
            record?.comsRatio = contactPlans.first { it.productPlanId == record?.id }.comsRatio
        }
        return result
    }
}