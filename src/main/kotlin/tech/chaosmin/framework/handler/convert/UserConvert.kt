package tech.chaosmin.framework.handler.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.domain.entity.UserEntity
import tech.chaosmin.framework.domain.request.UserReq
import tech.chaosmin.framework.domain.response.UserResp
import tech.chaosmin.framework.handler.convert.base.BaseConvert

@Mapper
interface UserConvert : BaseConvert<UserEntity, UserReq, UserResp> {
    companion object {
        val INSTANCE: UserConvert = Mappers.getMapper(UserConvert::class.java)
    }
}