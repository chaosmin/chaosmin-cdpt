package tech.chaosmin.framework.domain.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.dataobject.User
import tech.chaosmin.framework.domain.request.share.UserShareRequestDTO
import tech.chaosmin.framework.domain.response.share.UserShareResponseDTO

@Mapper
interface UserConvert {
    companion object {
        val INSTANCE: UserConvert = Mappers.getMapper(UserConvert::class.java)
    }

    fun convertToShareResponse(user: User): UserShareResponseDTO

    fun convertToShareResponse(users: List<User>): List<UserShareResponseDTO>

    fun convertToBaseBean(requestDTO: UserShareRequestDTO): User
}