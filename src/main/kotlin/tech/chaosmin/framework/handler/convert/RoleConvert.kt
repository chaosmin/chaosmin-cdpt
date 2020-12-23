package tech.chaosmin.framework.handler.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.domain.entity.RoleEntity
import tech.chaosmin.framework.domain.request.RoleReq
import tech.chaosmin.framework.domain.response.RoleResp
import tech.chaosmin.framework.handler.convert.base.BaseConvert

@Mapper
interface RoleConvert : BaseConvert<RoleEntity, RoleReq, RoleResp> {
    companion object {
        val INSTANCE: RoleConvert = Mappers.getMapper(RoleConvert::class.java)
    }
}