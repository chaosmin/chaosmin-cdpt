package tech.chaosmin.framework.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.dao.convert.ProductPlanRateTableMapper
import tech.chaosmin.framework.dao.dataobject.ProductPlanRateTable
import tech.chaosmin.framework.domain.PageQuery
import tech.chaosmin.framework.domain.entity.ProductPlanRateTableEntity
import tech.chaosmin.framework.handler.logic.base.BaseQueryLogic
import tech.chaosmin.framework.service.ProductPlanRateTableService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class ProductPlanRateTableQueryLogic(private val productPlanRateTableService: ProductPlanRateTableService) :
    BaseQueryLogic<ProductPlanRateTableEntity, ProductPlanRateTable> {

    override fun get(id: Long): ProductPlanRateTableEntity? {
        val productPlanRateTable = productPlanRateTableService.getById(id)
        return if (productPlanRateTable == null) null
        else ProductPlanRateTableMapper.INSTANCE.convert2Entity(productPlanRateTable)
    }

    override fun page(cond: PageQuery<ProductPlanRateTable>): IPage<ProductPlanRateTableEntity> {
        val page = productPlanRateTableService.page(cond.page, cond.wrapper)
        return page.convert(ProductPlanRateTableMapper.INSTANCE::convert2Entity)
    }
}