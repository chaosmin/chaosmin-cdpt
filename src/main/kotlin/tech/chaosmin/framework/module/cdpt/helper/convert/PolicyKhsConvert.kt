package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.cdpt.entity.PolicyKhsEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyKhsReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyKhsResp

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper
interface PolicyKhsConvert : BaseConvert<PolicyKhsEntity, PolicyKhsReq, PolicyKhsResp> {
    companion object {
        val INSTANCE: PolicyKhsConvert = Mappers.getMapper(PolicyKhsConvert::class.java)
    }
}