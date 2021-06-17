package tech.chaosmin.framework.module.cdpt.service.inner.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.PlanLiabilityDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanLiability
import tech.chaosmin.framework.module.cdpt.service.inner.PlanLiabilityService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class PlanLiabilityServiceImpl : ServiceImpl<PlanLiabilityDAO, PlanLiability>(), PlanLiabilityService {
    @Cacheable(value = ["product-plan-liability"], key = "#productPlanId", unless = "#result == null")
    override fun listByPlanId(productPlanId: Long): List<PlanLiability> {
        val ew = Wrappers.query<PlanLiability>().eq("product_plan_id", productPlanId)
            .orderBy(true, true, "sort")
        return baseMapper.selectList(ew)
    }
}