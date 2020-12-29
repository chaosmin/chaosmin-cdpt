package tech.chaosmin.framework.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.dao.convert.ProductPlanLiabilityMapper
import tech.chaosmin.framework.dao.dataobject.ProductPlanLiability
import tech.chaosmin.framework.dao.dataobject.ext.ProductPlanLiabilityExt
import tech.chaosmin.framework.domain.PageQuery
import tech.chaosmin.framework.domain.entity.ProductPlanLiabilityEntity
import tech.chaosmin.framework.handler.logic.base.BaseQueryLogic
import tech.chaosmin.framework.service.ProductPlanLiabilityService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class ProductPlanLiabilityQueryLogic(private val productPlanLiabilityService: ProductPlanLiabilityService) :
    BaseQueryLogic<ProductPlanLiabilityEntity, ProductPlanLiabilityExt> {

    override fun get(id: Long): ProductPlanLiabilityEntity? {
        val productPlanLiability = productPlanLiabilityService.getById(id)
        return if (productPlanLiability == null) null
        else ProductPlanLiabilityMapper.INSTANCE.convert2Entity(productPlanLiability)
    }

    override fun page(cond: PageQuery<ProductPlanLiabilityExt>): IPage<ProductPlanLiabilityEntity> {
        val page = productPlanLiabilityService.pageExt(cond.page, cond.wrapper)
        return page.convert(ProductPlanLiabilityMapper.INSTANCE::convert2Entity)
    }
}