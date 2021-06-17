package tech.chaosmin.framework.module.cdpt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.ProductCategoryEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.ProductCategoryMapper
import tech.chaosmin.framework.module.cdpt.service.inner.ProductCategoryService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyProductCategoryHandler(private val productCategoryService: ProductCategoryService) :
    AbstractTemplateOperate<ProductCategoryEntity, ProductCategoryEntity>() {
    override fun validation(arg: ProductCategoryEntity, result: RestResult<ProductCategoryEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    @Transactional
    override fun processor(
        arg: ProductCategoryEntity,
        result: RestResult<ProductCategoryEntity>
    ): RestResult<ProductCategoryEntity> {
        val productCategory = ProductCategoryMapper.INSTANCE.convert2DO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> productCategoryService.save(productCategory)
            ModifyTypeEnum.UPDATE -> productCategoryService.updateById(productCategory)
            ModifyTypeEnum.REMOVE -> productCategoryService.remove(Wrappers.query(productCategory))
        }
        return result.success(arg)
    }
}