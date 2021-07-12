package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.entity.PolicyInsurantEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyInsurantReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyInsurantResp

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface PolicyInsurantConvert {
    companion object {
        val INSTANCE: PolicyInsurantConvert = Mappers.getMapper(PolicyInsurantConvert::class.java)
    }

    fun convert2Resp(source: PolicyInsurantEntity): PolicyInsurantResp

    fun convert2Resp(source: List<PolicyInsurantEntity>): List<PolicyInsurantResp>

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
    fun convert2Entity(request: PolicyInsurantReq): PolicyInsurantEntity
}