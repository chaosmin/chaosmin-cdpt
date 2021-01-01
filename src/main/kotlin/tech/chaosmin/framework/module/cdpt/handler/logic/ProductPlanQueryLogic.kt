package tech.chaosmin.framework.module.cdpt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductPlanExt
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.ProductPlanMapper
import tech.chaosmin.framework.module.cdpt.service.ProductPlanService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class ProductPlanQueryLogic(private val productPlanService: ProductPlanService) :
    BaseQueryLogic<ProductPlanEntity, ProductPlanExt> {

    override fun get(id: Long): ProductPlanEntity? {
        val productPlan = productPlanService.getById(id)
        return if (productPlan == null) null
        else ProductPlanMapper.INSTANCE.convert2Entity(productPlan)
    }

    override fun page(cond: PageQuery<ProductPlanExt>): IPage<ProductPlanEntity> {
        val page = productPlanService.pageExt(cond.page, cond.wrapper)
        return page.convert(ProductPlanMapper.INSTANCE::convert2Entity)
    }
}