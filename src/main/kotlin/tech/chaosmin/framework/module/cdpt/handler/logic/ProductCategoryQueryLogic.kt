package tech.chaosmin.framework.module.cdpt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductCategory
import tech.chaosmin.framework.module.cdpt.entity.ProductCategoryEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.ProductCategoryMapper
import tech.chaosmin.framework.module.cdpt.service.inner.ProductCategoryService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class ProductCategoryQueryLogic(private val productCategoryService: ProductCategoryService) :
    BaseQueryLogic<ProductCategoryEntity, ProductCategory> {

    override fun get(id: Long): ProductCategoryEntity? {
        val productCategory = productCategoryService.getById(id)
        return ProductCategoryMapper.INSTANCE.convert2Entity(productCategory)
    }

    override fun page(cond: PageQuery<ProductCategory>): IPage<ProductCategoryEntity?> {
        val page = productCategoryService.page(cond.page, cond.wrapper)
        return page.convert(ProductCategoryMapper.INSTANCE::convert2Entity)
    }
}