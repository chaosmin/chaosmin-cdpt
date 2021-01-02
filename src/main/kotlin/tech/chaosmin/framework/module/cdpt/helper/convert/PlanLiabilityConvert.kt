package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.cdpt.entity.PlanLiabilityEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PlanLiabilityReq
import tech.chaosmin.framework.module.cdpt.entity.response.PlanLiabilityResp

/**
 * @author Romani min
 * @since 2020/12/23 21:30
 */
@Mapper
interface PlanLiabilityConvert : BaseConvert<PlanLiabilityEntity, PlanLiabilityReq, PlanLiabilityResp> {
    companion object {
        val INSTANCE: PlanLiabilityConvert = Mappers.getMapper(PlanLiabilityConvert::class.java)
    }
}