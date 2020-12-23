package tech.chaosmin.framework.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.convert.ProductCategoryMapper
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.entity.ProductCategoryEntity
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.handler.base.AbstractTemplateOperate
import tech.chaosmin.framework.service.ProductCategoryService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyProductCategoryHandler(private val productCategoryService: ProductCategoryService) :
    AbstractTemplateOperate<ProductCategoryEntity, ProductCategoryEntity>() {
    override fun validation(arg: ProductCategoryEntity, result: RestResult<ProductCategoryEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType");
        }
    }

    @Transactional
    override fun processor(
        arg: ProductCategoryEntity,
        result: RestResult<ProductCategoryEntity>
    ): RestResult<ProductCategoryEntity> {
        val productCategory = ProductCategoryMapper.INSTANCE.convert2DO(arg)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> productCategoryService.save(productCategory)
            ModifyTypeEnum.UPDATE -> productCategoryService.updateById(productCategory)
            ModifyTypeEnum.REMOVE -> productCategoryService.remove(Wrappers.query(productCategory))
        }
        return result.success(arg)
    }
}