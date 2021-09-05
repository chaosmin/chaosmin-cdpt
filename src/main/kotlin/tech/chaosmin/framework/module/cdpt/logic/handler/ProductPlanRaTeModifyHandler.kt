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
import tech.chaosmin.framework.module.cdpt.domain.service.ProductPlanRaTeService
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanRaTeEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.ProductPlanRaTeMapper

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ProductPlanRaTeModifyHandler(private val productPlanRaTeService: ProductPlanRaTeService) :
    AbstractTemplateOperate<ProductPlanRaTeEntity, ProductPlanRaTeEntity>() {
    override fun validation(arg: ProductPlanRaTeEntity, res: RestResult<ProductPlanRaTeEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    @Transactional
    override fun processor(
        arg: ProductPlanRaTeEntity,
        res: RestResult<ProductPlanRaTeEntity>
    ): RestResult<ProductPlanRaTeEntity> {
        val planRateTable = ProductPlanRaTeMapper.INSTANCE.toDO(arg)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> productPlanRaTeService.save(planRateTable)
            ModifyTypeEnum.UPDATE -> productPlanRaTeService.updateById(planRateTable)
            ModifyTypeEnum.REMOVE -> productPlanRaTeService.remove(Wrappers.query(planRateTable))
        }
        return res.success(arg)
    }
}