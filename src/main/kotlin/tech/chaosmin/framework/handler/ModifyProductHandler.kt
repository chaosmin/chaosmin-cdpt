package tech.chaosmin.framework.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.convert.ProductMapper
import tech.chaosmin.framework.dao.dataobject.Product
import tech.chaosmin.framework.dao.dataobject.ProductExternal
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.entity.ProductEntity
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.handler.base.AbstractTemplateOperate
import tech.chaosmin.framework.service.ProductCategoryService
import tech.chaosmin.framework.service.ProductExternalService
import tech.chaosmin.framework.service.ProductService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyProductHandler(
    private val productService: ProductService,
    private val productCategoryService: ProductCategoryService,
    private val productExternalService: ProductExternalService
) :
    AbstractTemplateOperate<ProductEntity, ProductEntity>() {
    override fun validation(arg: ProductEntity, result: RestResult<ProductEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType");
        }
    }

    @Transactional
    override fun processor(arg: ProductEntity, result: RestResult<ProductEntity>): RestResult<ProductEntity> {
        val product = ProductMapper.INSTANCE.convert2DO(arg)
        val productExternal = ProductExternal(arg.externalText)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> {
                val category = productCategoryService.getCategoryOrCreate(arg.categoryName!!, arg.categorySubName!!)
                // 针对productCode进行幂等查询
                val productDO = productService.listEqProductCode(arg.productCode!!).firstOrNull()
                if (productDO == null) {
                    createProduct(product, category.id, productExternal)
                } else {
                    // 幂等更新数据
                    product.id = productDO.id
                    updateProduct(product, category.id, productExternal)
                }
            }
            ModifyTypeEnum.UPDATE -> updateProduct(product, arg.productCategoryId, productExternal)
            ModifyTypeEnum.REMOVE -> productService.remove(Wrappers.query(product))
        }
        return result.success(ProductMapper.INSTANCE.convert2Entity(product))
    }

    private fun createProduct(product: Product, categoryId: Long?, external: ProductExternal) {
        productService.save(product)
        external.productId = product.id
        productExternalService.save(external)
        if (categoryId != null) {
            productService.setCategories(product.id!!, listOf(categoryId))
        }
    }

    private fun updateProduct(product: Product, categoryId: Long?, ex: ProductExternal) {
        productService.updateById(product)
        productExternalService.updateText(product.id!!, ex.externalText)
        if (categoryId != null) {
            productService.setCategories(product.id!!, listOf(categoryId))
        }
    }
}