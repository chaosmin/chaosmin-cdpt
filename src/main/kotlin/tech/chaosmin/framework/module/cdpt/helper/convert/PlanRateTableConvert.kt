package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.module.cdpt.entity.PlanRateTableEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PlanRateTableReq
import tech.chaosmin.framework.module.cdpt.entity.response.PlanRateTableResp

/**
 * @author Romani min
 * @since 2020/12/23 21:30
 */
@Mapper
interface PlanRateTableConvert {
    companion object {
        val INSTANCE: PlanRateTableConvert = Mappers.getMapper(PlanRateTableConvert::class.java)
    }

    fun convert2Resp(source: PlanRateTableEntity): PlanRateTableResp

    fun convert2Resp(source: List<PlanRateTableEntity>): List<PlanRateTableResp>

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
    fun convert2Entity(request: PlanRateTableReq): PlanRateTableEntity
}