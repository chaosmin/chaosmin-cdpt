package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.convert.base.BaseMapper
import tech.chaosmin.framework.dao.convert.base.KeyValueEnumMapper
import tech.chaosmin.framework.dao.dataobject.User
import tech.chaosmin.framework.domain.entity.UserEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:13
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface UserMapper : BaseMapper<UserEntity, User> {
    companion object {
        val INSTANCE: UserMapper = Mappers.getMapper(UserMapper::class.java)
    }
}