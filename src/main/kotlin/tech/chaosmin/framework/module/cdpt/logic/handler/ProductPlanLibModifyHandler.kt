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
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.service.ProductPlanLibService
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanLibEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.ProductPlanLibMapper

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ProductPlanLibModifyHandler(private val productPlanLibService: ProductPlanLibService) :
    AbstractTemplateOperate<ProductPlanLibEntity, ProductPlanLibEntity>() {
    override fun validation(arg: ProductPlanLibEntity, res: RestResult<ProductPlanLibEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    @Transactional
    override fun processor(
        arg: ProductPlanLibEntity,
        res: RestResult<ProductPlanLibEntity>
    ): RestResult<ProductPlanLibEntity> {
        val planLiability = ProductPlanLibMapper.INSTANCE.toDO(arg)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> productPlanLibService.save(planLiability)
            ModifyTypeEnum.UPDATE -> productPlanLibService.updateById(planLiability)
            ModifyTypeEnum.REMOVE -> productPlanLibService.remove(Wrappers.query(planLiability))
        }
        return res.success(arg)
    }
}