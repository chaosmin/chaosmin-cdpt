package tech.chaosmin.framework.service

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.dao.dataobject.ProductPlanLiability
import tech.chaosmin.framework.dao.dataobject.ext.ProductPlanLiabilityExt

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface ProductPlanLiabilityService : IService<ProductPlanLiability> {
    fun pageExt(page: Page<ProductPlanLiabilityExt>, queryWrapper: Wrapper<ProductPlanLiabilityExt>): IPage<ProductPlanLiabilityExt>
}