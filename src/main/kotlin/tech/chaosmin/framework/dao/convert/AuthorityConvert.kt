package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.dataobject.Authority
import tech.chaosmin.framework.domain.request.AuthorityReq
import tech.chaosmin.framework.domain.response.AuthorityResp

@Mapper
interface AuthorityConvert {
    companion object {
        val INSTANCE: AuthorityConvert = Mappers.getMapper(AuthorityConvert::class.java)
    }

    fun convert2Resp(authority: Authority): AuthorityResp

    fun convert2Resp(authorities: List<Authority>): List<AuthorityResp>

    fun convert2Entity(req: AuthorityReq): Authority
}