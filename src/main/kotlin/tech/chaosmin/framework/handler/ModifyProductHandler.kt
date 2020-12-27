package tech.chaosmin.framework.handler

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
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
import tech.chaosmin.framework.service.ProductService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyProductHandler(
    private val productService: ProductService,
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
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> {
                val queryWrapper = QueryWrapper<Product>().eq("product_code", product.productCode)
                if (!productService.list(queryWrapper).isNullOrEmpty()) {
                    throw FrameworkException(ErrorCodeEnum.DATA_ERROR.code, "${product.productCode}已存在.")
                }
                productService.save(product)
                arg.categoryIds?.run { productService.setCategories(product.id!!, this) }
                productAgreementService.save(ProductAgreement().apply {
                    this.productId = product.id
                    if (!arg.specialAgreement.isNullOrEmpty()) {
                        val s = arg.specialAgreement?.mapIndexed { i, s -> "${i + 1}、$s" }?.joinToString("<br>\n");
                        this.specialAgreement = s
                    }
                    if (!arg.notice.isNullOrEmpty()) {
                        val s = arg.notice?.mapIndexed { i, s -> "${i + 1}、$s" }?.joinToString("<br>\n")
                        this.notice = s
                    }
                })
            }
            ModifyTypeEnum.UPDATE -> productService.updateById(product)
            ModifyTypeEnum.REMOVE -> productService.remove(Wrappers.query(product))
        }
        return result.success(ProductMapper.INSTANCE.convert2Entity(product))
    }
}