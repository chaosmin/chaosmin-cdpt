package tech.chaosmin.framework.module.cdpt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanRateTable
import tech.chaosmin.framework.module.cdpt.entity.PlanLiabilityEntity
import tech.chaosmin.framework.module.cdpt.entity.PlanRateTableEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.PlanLiabilityMapper
import tech.chaosmin.framework.module.cdpt.helper.mapper.PlanRateTableMapper
import tech.chaosmin.framework.module.cdpt.service.PlanRateTableService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class PlanRateTableQueryLogic(private val planRateTableService: PlanRateTableService) :
    BaseQueryLogic<PlanRateTableEntity, PlanRateTable> {

    override fun get(id: Long): PlanRateTableEntity? {
        val planRateTable = planRateTableService.getById(id)
        return PlanRateTableMapper.INSTANCE.convert2Entity(planRateTable)
    }

    override fun page(cond: PageQuery<PlanRateTable>): IPage<PlanRateTableEntity?> {
        val page = planRateTableService.page(cond.page, cond.wrapper)
        return page.convert(PlanRateTableMapper.INSTANCE::convert2Entity)
    }

    fun fetchAllOfPlan(productPlanId: Long): List<PlanRateTableEntity?> {
        return planRateTableService.listByPlanId(productPlanId).map { PlanRateTableMapper.INSTANCE.convert2Entity(it) }
    }
}