package tech.chaosmin.framework.module.cdpt.service.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.PlanRateTableDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanRateTable
import tech.chaosmin.framework.module.cdpt.service.PlanRateTableService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class PlanRateTableServiceImpl : ServiceImpl<PlanRateTableDAO, PlanRateTable>(), PlanRateTableService {
    @Cacheable(value = ["product-plan-rate-table"], key = "#productPlanId", unless = "#result == null")
    override fun listByPlanId(productPlanId: Long): List<PlanRateTable> {
        val ew = Wrappers.query<PlanRateTable>().eq("product_plan_id", productPlanId)
            .orderBy(true, true, "sort")
        return baseMapper.selectList(ew)
    }
}