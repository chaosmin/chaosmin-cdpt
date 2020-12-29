package tech.chaosmin.framework.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.convert.ProductMapper
import tech.chaosmin.framework.dao.dataobject.Product
import tech.chaosmin.framework.dao.dataobject.ProductAgreement
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.entity.ProductEntity
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.handler.base.AbstractTemplateOperate
import tech.chaosmin.framework.service.ProductAgreementService
import tech.chaosmin.framework.service.ProductCategoryService
import tech.chaosmin.framework.service.ProductService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyProductHandler(
    private val productService: ProductService,
    private val productCategoryService: ProductCategoryService,
    private val productAgreementService: ProductAgreementService
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
        val productAgreement = ProductAgreement().apply {
            if (!arg.specialAgreement.isNullOrEmpty()) {
                this.specialAgreement = arg.specialAgreement?.joinToString("<br>\n");
            }
            if (!arg.notice.isNullOrEmpty()) {
                this.notice = arg.notice?.joinToString("<br>\n")
            }
        }
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> {
                val category = productCategoryService.getCategoryOrCreate(arg.categoryName!!, arg.categorySubName!!)
                // 针对productCode进行幂等查询
                val productDO = productService.listEqProductCode(arg.productCode!!).firstOrNull()
                if (productDO == null) {
                    createProduct(product, productAgreement)
                    productService.setCategories(product.id!!, listOf(category.id!!))
                } else {
                    // 幂等更新数据
                    product.id = productDO.id
                    updateProduct(product, productAgreement)
                }
            }
            ModifyTypeEnum.UPDATE -> updateProduct(product, productAgreement)
            ModifyTypeEnum.REMOVE -> productService.remove(Wrappers.query(product))
        }
        return result.success(ProductMapper.INSTANCE.convert2Entity(product))
    }

    private fun createProduct(product: Product, productAgreement: ProductAgreement) {
        productService.save(product)
        productAgreement.productId = product.id
        productAgreementService.save(productAgreement)
    }

    private fun updateProduct(product: Product, productAgreement: ProductAgreement) {
        productService.updateById(product)
        val wa = Wrappers.query<ProductAgreement>().eq("product_id", product.id)
        productAgreementService.update(productAgreement, wa)
    }
}