package tech.chaosmin.framework.module.cdpt.service

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.base.enums.BasicStatusEnum
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductPlan
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductPlanExt

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface ProductPlanService : IService<ProductPlan> {
    fun pageExt(page: Page<ProductPlanExt>, queryWrapper: Wrapper<ProductPlanExt>): IPage<ProductPlanExt>

    fun listEqProductId(productId: Long): List<ProductPlan>

    fun switchPlansTo(productId: Long, status: BasicStatusEnum)
}