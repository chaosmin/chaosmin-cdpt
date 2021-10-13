/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * ProductPlanInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.interrogator

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductPlanEx
import tech.chaosmin.framework.module.cdpt.domain.service.GoodsPlanService
import tech.chaosmin.framework.module.cdpt.domain.service.ProductPlanService
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.ProductPlanMapper

/**
 * @author Romani min
 * @since 2021/9/4 22:27
 */
@Component
class ProductPlanInterrogator(
    private val productPlanService: ProductPlanService,
    private val goodsPlanService: GoodsPlanService
) : Interrogator<ProductPlanEntity, ProductPlanEx> {
    override fun getOne(id: Long): ProductPlanEntity? {
        val productPlan = productPlanService.getById(id)
        return ProductPlanMapper.INSTANCE.toEn(productPlan)
    }

    override fun page(cond: PageQuery<ProductPlanEx>): IPage<ProductPlanEntity> {
        val page = productPlanService.pageExt(cond.page, cond.wrapper)
        return page.convert(ProductPlanMapper.INSTANCE::exToEn)
    }

    /**
     * 获取可授权产品计划列表
     */
    fun contract(userId: Long): IPage<ProductPlanEntity> {
        // 获取当前用户可授权的产品计划信息
        val contactPlans = goodsPlanService.getByUser(userId)
        if (contactPlans.isEmpty()) {
            // 可授权为零
            return Page<ProductPlanEntity>()
        }
        val ew = PageQuery.inQuery<ProductPlanEx>("product_plan.id", contactPlans.mapNotNull { it.productPlanId })
        val result = this.page(ew)
        // 更新一下可用的授权佣金比例(最大为自己的佣金上限)
        result.records.forEach { record ->
            record?.comsRatio = contactPlans.first { it.productPlanId == record?.id }.comsRatio
        }
        return result
    }
}