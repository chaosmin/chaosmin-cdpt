package tech.chaosmin.framework.module.mgmt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseMapper
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.mgmt.domain.dataobject.User
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.UserExt
import tech.chaosmin.framework.module.mgmt.entity.UserEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:13
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface UserMapper : BaseMapper<UserEntity, User> {
    companion object {
        val INSTANCE: UserMapper = Mappers.getMapper(UserMapper::class.java)
    }

    @Mappings(
        value = [
            Mapping(
                target = "roleIds",
                expression = "java(source.getRoles().stream().map(i -> i.getId()).collect(java.util.stream.Collectors.toList()))"
            ),
            Mapping(
                target = "role",
                expression = "java(source.getRoles().stream().map(i -> i.getName()).collect(java.util.stream.Collectors.joining(\",\")))"
            )
        ]
    )
    fun convertEx2Entity(source: UserExt?): UserEntity?

    fun convertEx2Entity(source: List<UserExt>): List<UserEntity>
}