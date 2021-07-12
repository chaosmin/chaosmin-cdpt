package tech.chaosmin.framework.module.mgmt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.module.mgmt.entity.AuthorityEntity
import tech.chaosmin.framework.module.mgmt.entity.request.AuthorityReq
import tech.chaosmin.framework.module.mgmt.entity.response.AuthorityResp

@Mapper
interface AuthorityConvert {
    companion object {
        val INSTANCE: AuthorityConvert = Mappers.getMapper(AuthorityConvert::class.java)
    }

    fun convert2Resp(source: AuthorityEntity): AuthorityResp

    fun convert2Resp(source: List<AuthorityEntity>): List<AuthorityResp>

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
    fun convert2Entity(request: AuthorityReq): AuthorityEntity

}