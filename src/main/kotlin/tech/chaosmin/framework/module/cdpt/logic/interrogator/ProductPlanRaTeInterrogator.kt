/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * ProductPlanRaTeInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.interrogator

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductPlanRaTe
import tech.chaosmin.framework.module.cdpt.domain.service.ProductPlanRaTeService
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanRaTeEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.ProductPlanRaTeMapper

/**
 * @author Romani min
 * @since 2021/9/5 00:56
 */
@Component
class ProductPlanRaTeInterrogator(private val productPlanRaTeService: ProductPlanRaTeService) : Interrogator<ProductPlanRaTeEntity, ProductPlanRaTe> {
    override fun getOne(id: Long): ProductPlanRaTeEntity? {
        val productPlan = productPlanRaTeService.getById(id)
        return ProductPlanRaTeMapper.INSTANCE.toEn(productPlan)
    }

    override fun page(cond: PageQuery<ProductPlanRaTe>): IPage<ProductPlanRaTeEntity> {
        val page = productPlanRaTeService.page(cond.page, cond.wrapper)
        return page.convert(ProductPlanRaTeMapper.INSTANCE::toEn)
    }
}