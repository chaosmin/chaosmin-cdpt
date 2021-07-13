package tech.chaosmin.framework.module.mgmt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.module.mgmt.entity.UserEntity
import tech.chaosmin.framework.module.mgmt.entity.request.UserReq
import tech.chaosmin.framework.module.mgmt.entity.response.UserResp

@Mapper
interface UserConvert {
    companion object {
        val INSTANCE: UserConvert = Mappers.getMapper(UserConvert::class.java)
    }

    fun convert2Resp(source: UserEntity): UserResp

    fun convert2Resp(source: List<UserEntity>): List<UserResp>

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
    fun convert2Entity(request: UserReq): UserEntity

}