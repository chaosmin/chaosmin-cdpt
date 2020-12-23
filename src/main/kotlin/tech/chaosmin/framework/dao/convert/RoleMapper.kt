package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.convert.base.BaseMapper
import tech.chaosmin.framework.dao.dataobject.Role
import tech.chaosmin.framework.domain.entity.RoleEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:13
 */
@Mapper
interface RoleMapper : BaseMapper<RoleEntity, Role> {
    companion object {
        val INSTANCE: RoleMapper = Mappers.getMapper(RoleMapper::class.java)
    }
}