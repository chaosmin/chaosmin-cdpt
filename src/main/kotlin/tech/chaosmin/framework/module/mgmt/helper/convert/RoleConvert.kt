package tech.chaosmin.framework.module.mgmt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.mgmt.entity.RoleEntity
import tech.chaosmin.framework.module.mgmt.entity.request.RoleReq
import tech.chaosmin.framework.module.mgmt.entity.response.RoleResp

@Mapper
interface RoleConvert : BaseConvert<RoleEntity, RoleReq, RoleResp> {
    companion object {
        val INSTANCE: RoleConvert = Mappers.getMapper(RoleConvert::class.java)
    }
}