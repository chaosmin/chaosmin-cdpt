package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.ProductPlanDAO
import tech.chaosmin.framework.dao.dataobject.ProductPlan
import tech.chaosmin.framework.dao.dataobject.ext.ProductPlanExt
import tech.chaosmin.framework.service.ProductPlanService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ProductPlanServiceImpl : ServiceImpl<ProductPlanDAO, ProductPlan>(), ProductPlanService {
    override fun pageExt(page: Page<ProductPlanExt>, queryWrapper: Wrapper<ProductPlanExt>): IPage<ProductPlanExt> {
        return baseMapper.pageExt(page, queryWrapper)
    }

    @Cacheable(value = ["product-plan:EqProductId"], key = "#productId", unless = "#result.isEmpty()")
    override fun listEqProductId(productId: Long): List<ProductPlan> {
        val wa = Wrappers.query<ProductPlan>().eq("product_id", productId)
        return baseMapper.selectList(wa)
    }
}