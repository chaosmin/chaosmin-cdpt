package tech.chaosmin.framework.module.cdpt.service.inner

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanRateTable

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface PlanRateTableService : IService<PlanRateTable> {
    fun listByPlanId(productPlanId: Long): List<PlanRateTable>
}