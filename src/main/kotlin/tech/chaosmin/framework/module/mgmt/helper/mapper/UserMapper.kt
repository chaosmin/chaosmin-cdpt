package tech.chaosmin.framework.module.mgmt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.mgmt.domain.dataobject.User
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.UserExt
import tech.chaosmin.framework.module.mgmt.entity.UserEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:13
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface UserMapper {
    companion object {
        val INSTANCE: UserMapper = Mappers.getMapper(UserMapper::class.java)
    }

    fun convert2DO(source: UserEntity?): User?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: User?): UserEntity?

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
    fun convert2Entity(source: UserExt?): UserEntity?

    fun convert2Entity(source: List<User>?): List<UserEntity>?
}