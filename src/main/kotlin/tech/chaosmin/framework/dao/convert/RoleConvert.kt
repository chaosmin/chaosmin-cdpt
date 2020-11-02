package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.dataobject.Role
import tech.chaosmin.framework.domain.request.share.RoleShareRequestDTO
import tech.chaosmin.framework.domain.response.share.RoleShareResponseDTO

@Mapper
interface RoleConvert {
    companion object {
        val INSTANCE: RoleConvert = Mappers.getMapper(RoleConvert::class.java)
    }

    fun convertToShareResponse(role: Role): RoleShareResponseDTO

    fun convertToShareResponse(roles: List<Role>): List<RoleShareResponseDTO>

    fun convertToBaseBean(requestDTO: RoleShareRequestDTO): Role
}