package tech.chaosmin.framework.module.mgmt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Authority
import tech.chaosmin.framework.module.mgmt.entity.AuthorityEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:13
 */
@Mapper
interface AuthorityMapper {
    companion object {
        val INSTANCE: AuthorityMapper = Mappers.getMapper(AuthorityMapper::class.java)
    }

    fun convert2DO(source: AuthorityEntity?): Authority?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: Authority?): AuthorityEntity?

    fun convert2Entity(source: List<Authority>?): List<AuthorityEntity>?
}