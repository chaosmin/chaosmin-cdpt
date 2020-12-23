package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.convert.base.BaseMapper
import tech.chaosmin.framework.dao.dataobject.Authority
import tech.chaosmin.framework.domain.entity.AuthorityEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:13
 */
@Mapper
interface AuthorityMapper : BaseMapper<AuthorityEntity, Authority> {
    companion object {
        val INSTANCE: AuthorityMapper = Mappers.getMapper(AuthorityMapper::class.java)
    }
}