/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * ProductPlanLibInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.interrogator

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductPlanLib
import tech.chaosmin.framework.module.cdpt.domain.service.ProductPlanLibService
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanLibEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.ProductPlanLibMapper

/**
 * @author Romani min
 * @since 2021/9/5 00:56
 */
@Component
class ProductPlanLibInterrogator(private val productPlanLibService: ProductPlanLibService) : Interrogator<ProductPlanLibEntity, ProductPlanLib> {
    override fun getOne(id: Long): ProductPlanLibEntity? {
        val productPlan = productPlanLibService.getById(id)
        return ProductPlanLibMapper.INSTANCE.toEn(productPlan)
    }

    override fun page(cond: PageQuery<ProductPlanLib>): IPage<ProductPlanLibEntity> {
        val page = productPlanLibService.page(cond.page, cond.wrapper)
        return page.convert(ProductPlanLibMapper.INSTANCE::toEn)
    }
}