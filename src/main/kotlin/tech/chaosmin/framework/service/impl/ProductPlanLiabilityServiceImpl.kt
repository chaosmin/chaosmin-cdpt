package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.ProductPlanLiabilityDAO
import tech.chaosmin.framework.dao.dataobject.ProductPlanLiability
import tech.chaosmin.framework.dao.dataobject.ext.ProductPlanLiabilityExt
import tech.chaosmin.framework.service.ProductPlanLiabilityService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ProductPlanLiabilityServiceImpl : ServiceImpl<ProductPlanLiabilityDAO, ProductPlanLiability>(),
    ProductPlanLiabilityService {
    override fun pageExt(
        page: Page<ProductPlanLiabilityExt>,
        queryWrapper: Wrapper<ProductPlanLiabilityExt>
    ): IPage<ProductPlanLiabilityExt> {
        return baseMapper.pageExt(page, queryWrapper)
    }
}