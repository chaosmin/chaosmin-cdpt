package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.module.cdpt.entity.PartnerEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PartnerReq
import tech.chaosmin.framework.module.cdpt.entity.response.PartnerResp

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper
interface PartnerConvert {
    companion object {
        val INSTANCE: PartnerConvert = Mappers.getMapper(PartnerConvert::class.java)
    }

    fun convert2Resp(source: PartnerEntity): PartnerResp

    fun convert2Resp(source: List<PartnerEntity>): List<PartnerResp>

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
    fun convert2Entity(request: PartnerReq): PartnerEntity
}