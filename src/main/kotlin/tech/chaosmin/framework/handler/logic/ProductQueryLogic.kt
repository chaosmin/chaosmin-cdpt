package tech.chaosmin.framework.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.dao.convert.ProductMapper
import tech.chaosmin.framework.dao.dataobject.ext.ProductExt
import tech.chaosmin.framework.domain.PageQuery
import tech.chaosmin.framework.domain.entity.ProductEntity
import tech.chaosmin.framework.handler.logic.base.BaseQueryLogic
import tech.chaosmin.framework.service.ProductExternalService
import tech.chaosmin.framework.service.ProductService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class ProductQueryLogic(
    private val productService: ProductService,
    private val productAgreementService: ProductExternalService
) : BaseQueryLogic<ProductEntity, ProductExt> {

    override fun get(id: Long): ProductEntity? {
        val product = productService.getById(id)
        return if (product == null) null
        else ProductMapper.INSTANCE.convert2Entity(product)
    }

    override fun page(cond: PageQuery<ProductExt>): IPage<ProductEntity> {
        val page = productService.pageExt(cond.page, cond.wrapper)
        val result = page.convert(ProductMapper.INSTANCE::convert2Entity)
        result.records.forEach {
            it.externalText = productAgreementService.selectText(it.id!!)
        }
        return result
    }
}