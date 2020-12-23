package tech.chaosmin.framework.handler.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.domain.entity.AuthorityEntity
import tech.chaosmin.framework.domain.request.AuthorityReq
import tech.chaosmin.framework.domain.response.AuthorityResp
import tech.chaosmin.framework.handler.convert.base.BaseConvert

@Mapper
interface AuthorityConvert : BaseConvert<AuthorityEntity, AuthorityReq, AuthorityResp> {
    companion object {
        val INSTANCE: AuthorityConvert = Mappers.getMapper(AuthorityConvert::class.java)
    }
}