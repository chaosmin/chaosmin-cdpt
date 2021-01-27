package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.entity.PolicyInsurantEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyInsurantReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyInsurantResp

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface PolicyInsurantConvert : BaseConvert<PolicyInsurantEntity, PolicyInsurantReq, PolicyInsurantResp> {
    companion object {
        val INSTANCE: PolicyInsurantConvert = Mappers.getMapper(PolicyInsurantConvert::class.java)
    }
}