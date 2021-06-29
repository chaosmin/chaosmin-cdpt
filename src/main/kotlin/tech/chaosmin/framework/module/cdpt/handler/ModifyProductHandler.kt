package tech.chaosmin.framework.module.cdpt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.base.enums.StatusEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Product
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductExternal
import tech.chaosmin.framework.module.cdpt.entity.ProductEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.ProductMapper
import tech.chaosmin.framework.module.cdpt.service.inner.ProductCategoryService
import tech.chaosmin.framework.module.cdpt.service.inner.ProductExternalService
import tech.chaosmin.framework.module.cdpt.service.inner.ProductPlanService
import tech.chaosmin.framework.module.cdpt.service.inner.ProductService
import tech.chaosmin.framework.utils.EnumClient

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyProductHandler(
    private val productService: ProductService,
    private val productPlanService: ProductPlanService,
    private val productCategoryService: ProductCategoryService,
    private val productExternalService: ProductExternalService
) : AbstractTemplateOperate<ProductEntity, ProductEntity>() {
    override fun validation(arg: ProductEntity, result: RestResult<ProductEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    @Transactional
    override fun processor(arg: ProductEntity, result: RestResult<ProductEntity>): RestResult<ProductEntity> {
        val product = ProductMapper.INSTANCE.convert2DO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        val productExternal = ProductExternal(arg.insuranceNotice, arg.externalText)
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
        val productId = product.id!!
        if (product.status != null && EnumClient.getEnum(StatusEnum::class.java, product.status!!) != null) {
            productPlanService.switchPlansTo(productId, EnumClient.getEnum(StatusEnum::class.java, product.status!!)!!)
        }
        productExternalService.updateByProductId(productId, ex)
        if (categoryId != null) {
            productService.setCategories(productId, listOf(categoryId))
        }
    }
}