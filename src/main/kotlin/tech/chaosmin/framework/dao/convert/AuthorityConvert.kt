package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.dataobject.Authority
import tech.chaosmin.framework.domain.request.share.AuthorityShareRequestDTO
import tech.chaosmin.framework.domain.response.share.AuthorityShareResponseDTO

@Mapper
interface AuthorityConvert {
    companion object {
        val INSTANCE: AuthorityConvert = Mappers.getMapper(AuthorityConvert::class.java)
    }

    fun convertToShareResponse(authority: Authority): AuthorityShareResponseDTO

    fun convertToShareResponse(authorities: List<Authority>): List<AuthorityShareResponseDTO>

    fun convertToBaseBean(requestDTO: AuthorityShareRequestDTO): Authority
}