package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyResp

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper
interface PolicyConvert : BaseConvert<PolicyEntity, PolicyReq, PolicyResp> {
    companion object {
        val INSTANCE: PolicyConvert = Mappers.getMapper(PolicyConvert::class.java)
    }
}