package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.entity.PolicyKhsEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyKhsReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyKhsResp

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface PolicyKhsConvert : BaseConvert<PolicyKhsEntity, PolicyKhsReq, PolicyKhsResp> {
    companion object {
        val INSTANCE: PolicyKhsConvert = Mappers.getMapper(PolicyKhsConvert::class.java)
    }

    @Mappings(
        value = [
            Mapping(target = "fileTime", source = "time"),
            Mapping(target = "resourceUrl", source = "url"),
            Mapping(target = "khsType", source = "type")
        ]
    )
    override fun convert2Entity(request: PolicyKhsReq): PolicyKhsEntity
}