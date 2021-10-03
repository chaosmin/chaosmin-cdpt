/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.domain.service.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.ProductPlanRaTeDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductPlanRaTe
import tech.chaosmin.framework.module.cdpt.domain.service.ProductPlanRaTeService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ProductPlanRaTeServiceImpl : ServiceImpl<ProductPlanRaTeDAO, ProductPlanRaTe>(), ProductPlanRaTeService {
    @Cacheable(value = ["product-plans"], key = "#productPlanId + ':rate-table'", unless = "#result == null")
    override fun listByPlanId(productPlanId: Long): List<ProductPlanRaTe> {
        val ew = Wrappers.query<ProductPlanRaTe>().eq("product_plan_id", productPlanId)
            .orderBy(true, true, "sort")
        return baseMapper.selectList(ew)
    }
}