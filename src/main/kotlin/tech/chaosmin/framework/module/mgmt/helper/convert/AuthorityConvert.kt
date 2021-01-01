package tech.chaosmin.framework.module.mgmt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.mgmt.entity.AuthorityEntity
import tech.chaosmin.framework.module.mgmt.entity.request.AuthorityReq
import tech.chaosmin.framework.module.mgmt.entity.response.AuthorityResp

@Mapper
interface AuthorityConvert : BaseConvert<AuthorityEntity, AuthorityReq, AuthorityResp> {
    companion object {
        val INSTANCE: AuthorityConvert = Mappers.getMapper(AuthorityConvert::class.java)
    }
}