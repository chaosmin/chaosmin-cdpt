package tech.chaosmin.framework.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.dao.convert.ProductCategoryMapper
import tech.chaosmin.framework.dao.dataobject.ProductCategory
import tech.chaosmin.framework.domain.PageQuery
import tech.chaosmin.framework.domain.entity.ProductCategoryEntity
import tech.chaosmin.framework.domain.response.ProductCategoryTreeNodeResp
import tech.chaosmin.framework.handler.logic.base.BaseQueryLogic
import tech.chaosmin.framework.service.ProductCategoryService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class ProductCategoryQueryLogic(private val productCategoryService: ProductCategoryService) :
    BaseQueryLogic<ProductCategoryEntity, ProductCategory> {

    override fun get(id: Long): ProductCategoryEntity? {
        val productCategory = productCategoryService.getById(id)
        return if (productCategory == null) null
        else ProductCategoryMapper.INSTANCE.convert2Entity(productCategory)
    }

    override fun page(cond: PageQuery<ProductCategory>): IPage<ProductCategoryEntity> {
        val page = productCategoryService.page(cond.page, cond.wrapper)
        return page.convert(ProductCategoryMapper.INSTANCE::convert2Entity)
    }

    fun tree(): List<ProductCategoryTreeNodeResp> {
        val list = productCategoryService.list()
        val result = mutableListOf<ProductCategoryTreeNodeResp>()
        list.groupBy { it.parentName }.forEach { (p, l) ->
            result.add(ProductCategoryTreeNodeResp(label = p, list = l))
        }
        return result
    }
}