package tech.chaosmin.framework.module.mgmt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.module.mgmt.entity.ResDataEntity
import tech.chaosmin.framework.module.mgmt.entity.request.ResDataReq
import tech.chaosmin.framework.module.mgmt.entity.response.ResDataResp

@Mapper
interface ResDataConvert {
    companion object {
        val INSTANCE: ResDataConvert = Mappers.getMapper(ResDataConvert::class.java)
    }

    fun convert2Resp(source: ResDataEntity): ResDataResp

    fun convert2Resp(source: List<ResDataEntity>): List<ResDataResp>

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
    fun convert2Entity(request: ResDataReq): ResDataEntity

}