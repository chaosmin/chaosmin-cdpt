package tech.chaosmin.framework.module.cdpt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductExt
import tech.chaosmin.framework.module.cdpt.entity.ProductEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.ProductMapper
import tech.chaosmin.framework.module.cdpt.service.inner.ProductExternalService
import tech.chaosmin.framework.module.cdpt.service.inner.ProductService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class ProductQueryLogic(
    private val productService: ProductService,
    private val productExternalService: ProductExternalService
) : BaseQueryLogic<ProductEntity, ProductExt> {

    override fun get(id: Long): ProductEntity? {
        val productExt = productService.getByIdExt(id)
        return ProductMapper.INSTANCE.convert2Entity(productExt)
    }

    override fun page(cond: PageQuery<ProductExt>): IPage<ProductEntity> {
        val page = productService.pageExt(cond.page, cond.wrapper)
        val result = page.convert {
            ProductMapper.INSTANCE.convert2Entity(it)!!.apply {
                val ex = productExternalService.getByProductId(it.id!!)
                this.insuranceNotice = ex.insuranceNotice
                this.externalText = ex.externalText
            }
        }
        return result
    }
}