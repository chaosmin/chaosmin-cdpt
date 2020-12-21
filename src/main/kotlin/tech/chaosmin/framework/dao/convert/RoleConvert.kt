package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.dataobject.Role
import tech.chaosmin.framework.domain.request.RoleReq
import tech.chaosmin.framework.domain.response.RoleResp

@Mapper
interface RoleConvert {
    companion object {
        val INSTANCE: RoleConvert = Mappers.getMapper(RoleConvert::class.java)
    }

    fun convert2Resp(role: Role): RoleResp

    fun convert2Resp(roles: List<Role>): List<RoleResp>

    fun convert2Entity(req: RoleReq): Role
}