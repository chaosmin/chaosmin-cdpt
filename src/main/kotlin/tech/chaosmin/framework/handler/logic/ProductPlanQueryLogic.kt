package tech.chaosmin.framework.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.dao.convert.ProductPlanMapper
import tech.chaosmin.framework.dao.dataobject.ProductPlan
import tech.chaosmin.framework.dao.dataobject.ext.ProductPlanExt
import tech.chaosmin.framework.domain.PageQuery
import tech.chaosmin.framework.domain.entity.ProductPlanEntity
import tech.chaosmin.framework.handler.logic.base.BaseQueryLogic
import tech.chaosmin.framework.service.ProductPlanService

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