package tech.chaosmin.framework.module.cdpt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanLiability
import tech.chaosmin.framework.module.cdpt.entity.PlanLiabilityEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.PlanLiabilityMapper
import tech.chaosmin.framework.module.cdpt.service.inner.PlanLiabilityService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class PlanLiabilityQueryLogic(private val planLiabilityService: PlanLiabilityService) :
    BaseQueryLogic<PlanLiabilityEntity, PlanLiability> {

    override fun get(id: Long): PlanLiabilityEntity? {
        val planLiability = planLiabilityService.getById(id)
        return PlanLiabilityMapper.INSTANCE.convert2Entity(planLiability)
    }

    override fun page(cond: PageQuery<PlanLiability>): IPage<PlanLiabilityEntity?> {
        val page = planLiabilityService.page(cond.page, cond.wrapper)
        return page.convert(PlanLiabilityMapper.INSTANCE::convert2Entity)
    }

    fun fetchAllOfPlan(productPlanId: Long): List<PlanLiabilityEntity?> {
        return planLiabilityService.listByPlanId(productPlanId).map { PlanLiabilityMapper.INSTANCE.convert2Entity(it) }
    }
}