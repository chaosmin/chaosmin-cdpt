package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.dataobject.User
import tech.chaosmin.framework.domain.request.UserReq
import tech.chaosmin.framework.domain.response.UserResp

@Mapper
interface UserConvert {
    companion object {
        val INSTANCE: UserConvert = Mappers.getMapper(UserConvert::class.java)
    }

    fun convert2Resp(user: User): UserResp

    fun convert2Resp(users: List<User>): List<UserResp>

    fun convert2Entity(req: UserReq): User
}