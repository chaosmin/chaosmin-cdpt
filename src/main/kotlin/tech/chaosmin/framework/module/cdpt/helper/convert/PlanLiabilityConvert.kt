package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.module.cdpt.entity.PlanLiabilityEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PlanLiabilityReq
import tech.chaosmin.framework.module.cdpt.entity.response.PlanLiabilityResp

/**
 * @author Romani min
 * @since 2020/12/23 21:30
 */
@Mapper
interface PlanLiabilityConvert {
    companion object {
        val INSTANCE: PlanLiabilityConvert = Mappers.getMapper(PlanLiabilityConvert::class.java)
    }

    fun convert2Resp(source: PlanLiabilityEntity): PlanLiabilityResp

    fun convert2Resp(source: List<PlanLiabilityEntity>): List<PlanLiabilityResp>

    @Mappings(
        value = [
            Mapping(target = "id", ignore = true),
            Mapping(target = "modifyType", ignore = true),
            Mapping(target = "createTime", ignore = true),
            Mapping(target = "creator", ignore = true),
            Mapping(target = "updateTime", ignore = true),
            Mapping(target = "updater", ignore = true),
            Mapping(target = "deleted", ignore = true)
        ]
    )
    fun convert2Entity(request: PlanLiabilityReq): PlanLiabilityEntity
}