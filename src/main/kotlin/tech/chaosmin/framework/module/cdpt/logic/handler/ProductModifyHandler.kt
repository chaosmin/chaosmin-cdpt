/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.handler

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
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductRichText
import tech.chaosmin.framework.module.cdpt.domain.service.ProductCategoryService
import tech.chaosmin.framework.module.cdpt.domain.service.ProductPlanService
import tech.chaosmin.framework.module.cdpt.domain.service.ProductRichTextService
import tech.chaosmin.framework.module.cdpt.domain.service.ProductService
import tech.chaosmin.framework.module.cdpt.entity.ProductEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.ProductMapper
import tech.chaosmin.framework.utils.EnumClient

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ProductModifyHandler(
    private val productService: ProductService,
    private val productPlanService: ProductPlanService,
    private val productCategoryService: ProductCategoryService,
    private val productRichTextService: ProductRichTextService
) : AbstractTemplateOperate<ProductEntity, ProductEntity>() {
    override fun validation(arg: ProductEntity, res: RestResult<ProductEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    @Transactional
    override fun processor(arg: ProductEntity, res: RestResult<ProductEntity>): RestResult<ProductEntity> {
        val product = ProductMapper.INSTANCE.toDO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        val productRichText = ProductRichText(arg.insuranceNotice, arg.externalText)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> {
                val category = productCategoryService.getCategoryOrCreate(arg.categoryName!!, arg.categorySubName!!)
                // 针对productCode进行幂等查询
                val productDO = productService.listEqProductCode(arg.productCode!!).firstOrNull()
                if (productDO == null) {
                    createProduct(product, category.id, productRichText)
                } else {
                    // 幂等更新数据
                    product.id = productDO.id
                    updateProduct(product, category.id, productRichText)
                }
            }
            ModifyTypeEnum.UPDATE -> updateProduct(product, arg.productCategoryId, productRichText)
            ModifyTypeEnum.REMOVE -> productService.remove(Wrappers.query(product))
        }
        return res.success(ProductMapper.INSTANCE.toEn(product))
    }

    private fun createProduct(product: Product, categoryId: Long?, external: ProductRichText) {
        productService.save(product)
        external.productId = product.id
        productRichTextService.save(external)
        if (categoryId != null) {
            productService.setCategories(product.id!!, listOf(categoryId))
        }
    }

    private fun updateProduct(product: Product, categoryId: Long?, ex: ProductRichText) {
        productService.updateById(product)
        val productId = product.id!!
        if (product.status != null && EnumClient.getEnum(StatusEnum::class.java, product.status!!) != null) {
            productPlanService.switchPlansTo(productId, EnumClient.getEnum(StatusEnum::class.java, product.status!!)!!)
        }
        productRichTextService.updateByProductId(productId, ex)
        if (categoryId != null) {
            productService.setCategories(productId, listOf(categoryId))
        }
    }
}