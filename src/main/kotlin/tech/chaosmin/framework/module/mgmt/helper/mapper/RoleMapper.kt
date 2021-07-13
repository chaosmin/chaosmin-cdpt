package tech.chaosmin.framework.module.mgmt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Role
import tech.chaosmin.framework.module.mgmt.entity.RoleEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:13
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface RoleMapper {
    companion object {
        val INSTANCE: RoleMapper = Mappers.getMapper(RoleMapper::class.java)
    }

    fun convert2DO(source: RoleEntity?): Role?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: Role?): RoleEntity?

    fun convert2Entity(source: List<Role>?): List<RoleEntity>?
}