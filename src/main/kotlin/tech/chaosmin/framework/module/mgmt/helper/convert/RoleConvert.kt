package tech.chaosmin.framework.module.mgmt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.module.mgmt.entity.RoleEntity
import tech.chaosmin.framework.module.mgmt.entity.request.RoleReq
import tech.chaosmin.framework.module.mgmt.entity.response.RoleResp

@Mapper
interface RoleConvert {
    companion object {
        val INSTANCE: RoleConvert = Mappers.getMapper(RoleConvert::class.java)
    }

    fun convert2Resp(source: RoleEntity): RoleResp

    fun convert2Resp(source: List<RoleEntity>): List<RoleResp>

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
    fun convert2Entity(request: RoleReq): RoleEntity

}