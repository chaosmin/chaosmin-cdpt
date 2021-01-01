package tech.chaosmin.framework.module.mgmt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.mgmt.entity.UserEntity
import tech.chaosmin.framework.module.mgmt.entity.request.UserReq
import tech.chaosmin.framework.module.mgmt.entity.response.UserResp

@Mapper
interface UserConvert : BaseConvert<UserEntity, UserReq, UserResp> {
    companion object {
        val INSTANCE: UserConvert = Mappers.getMapper(UserConvert::class.java)
    }
}