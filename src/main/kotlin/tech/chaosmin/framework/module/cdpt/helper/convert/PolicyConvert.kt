package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyResp

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper
interface PolicyConvert {
    companion object {
        val INSTANCE: PolicyConvert = Mappers.getMapper(PolicyConvert::class.java)
    }

    fun convert2Resp(source: PolicyEntity): PolicyResp

    fun convert2Resp(source: List<PolicyEntity>): List<PolicyResp>

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
    fun convert2Entity(request: PolicyReq): PolicyEntity
}