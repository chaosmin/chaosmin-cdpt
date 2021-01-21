package tech.chaosmin.framework.module.cdpt.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanLiability

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface PlanLiabilityService : IService<PlanLiability> {
    fun listByPlanId(productPlanId: Long): List<PlanLiability>
}