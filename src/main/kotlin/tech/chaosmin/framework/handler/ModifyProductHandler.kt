package tech.chaosmin.framework.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.convert.ProductMapper
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.entity.ProductEntity
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.handler.base.AbstractTemplateOperate
import tech.chaosmin.framework.service.ProductService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyProductHandler(private val productService: ProductService) :
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
            ModifyTypeEnum.SAVE -> productService.save(product)
            ModifyTypeEnum.UPDATE -> productService.updateById(product)
            ModifyTypeEnum.REMOVE -> productService.remove(Wrappers.query(product))
        }
        return result.success(arg)
    }
}