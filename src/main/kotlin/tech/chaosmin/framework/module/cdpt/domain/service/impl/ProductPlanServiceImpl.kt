/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.domain.service.impl

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.base.enums.StatusEnum
import tech.chaosmin.framework.module.cdpt.domain.dao.ProductPlanDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductPlan
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductPlanEx
import tech.chaosmin.framework.module.cdpt.domain.service.ProductPlanService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ProductPlanServiceImpl : ServiceImpl<ProductPlanDAO, ProductPlan>(), ProductPlanService {
    override fun pageExt(page: Page<ProductPlanEx>, queryWrapper: Wrapper<ProductPlanEx>): IPage<ProductPlanEx> {
        return baseMapper.pageExt(page, queryWrapper)
    }

    override fun listEqProductId(productId: Long): List<ProductPlan> {
        val wa = Wrappers.query<ProductPlan>().eq("product_id", productId)
        return baseMapper.selectList(wa)
    }

    override fun switchPlansTo(productId: Long, status: StatusEnum) {
        val statusEq = if (status == StatusEnum.ENABLED) StatusEnum.DISABLED.getCode() else StatusEnum.ENABLED.getCode()
        val wa = Wrappers.query<ProductPlan>().eq("product_id", productId).eq("status", statusEq)
        val productPlan = ProductPlan()
        productPlan.status = status.getCode()
        baseMapper.update(productPlan, wa)
    }
}