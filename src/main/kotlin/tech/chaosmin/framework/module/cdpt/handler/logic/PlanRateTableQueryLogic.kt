package tech.chaosmin.framework.module.cdpt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanRateTable
import tech.chaosmin.framework.module.cdpt.entity.PlanRateTableEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.PlanRateTableMapper
import tech.chaosmin.framework.module.cdpt.service.PlanRateTableService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class PlanRateTableQueryLogic(private val productPlanRateTableService: PlanRateTableService) :
    BaseQueryLogic<PlanRateTableEntity, PlanRateTable> {

    override fun get(id: Long): PlanRateTableEntity? {
        val productPlanRateTable = productPlanRateTableService.getById(id)
        return if (productPlanRateTable == null) null
        else PlanRateTableMapper.INSTANCE.convert2Entity(productPlanRateTable)
    }

    override fun page(cond: PageQuery<PlanRateTable>): IPage<PlanRateTableEntity> {
        val page = productPlanRateTableService.page(cond.page, cond.wrapper)
        return page.convert(PlanRateTableMapper.INSTANCE::convert2Entity)
    }
}