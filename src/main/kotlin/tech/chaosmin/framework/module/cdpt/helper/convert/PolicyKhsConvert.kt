package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.entity.PolicyKhsEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyKhsReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyKhsResp

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface PolicyKhsConvert {
    companion object {
        val INSTANCE: PolicyKhsConvert = Mappers.getMapper(PolicyKhsConvert::class.java)
    }

    fun convert2Resp(source: PolicyKhsEntity): PolicyKhsResp

    fun convert2Resp(source: List<PolicyKhsEntity>): List<PolicyKhsResp>

    @Mappings(
        value = [
            Mapping(target = "fileTime", source = "time"),
            Mapping(target = "resourceUrl", source = "url"),
            Mapping(target = "khsType", source = "type"),
            Mapping(target = "id", ignore = true),
            Mapping(target = "modifyType", ignore = true),
            Mapping(target = "createTime", ignore = true),
            Mapping(target = "creator", ignore = true),
            Mapping(target = "updateTime", ignore = true),
            Mapping(target = "updater", ignore = true),
            Mapping(target = "deleted", ignore = true)
        ]
    )
    fun convert2Entity(request: PolicyKhsReq): PolicyKhsEntity
}