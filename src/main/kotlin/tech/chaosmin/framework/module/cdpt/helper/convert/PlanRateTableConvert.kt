package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.cdpt.entity.PlanRateTableEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PlanRateTableReq
import tech.chaosmin.framework.module.cdpt.entity.response.PlanRateTableResp

/**
 * @author Romani min
 * @since 2020/12/23 21:30
 */
@Mapper
interface PlanRateTableConvert : BaseConvert<PlanRateTableEntity, PlanRateTableReq, PlanRateTableResp> {
    companion object {
        val INSTANCE: PlanRateTableConvert = Mappers.getMapper(PlanRateTableConvert::class.java)
    }
}