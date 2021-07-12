package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyInsurant
import tech.chaosmin.framework.module.cdpt.entity.PolicyInsurantEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface PolicyInsurantMapper {
    companion object {
        val INSTANCE: PolicyInsurantMapper = Mappers.getMapper(PolicyInsurantMapper::class.java)
    }

    fun convert2DO(source: PolicyInsurantEntity?): PolicyInsurant?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: PolicyInsurant?): PolicyInsurantEntity?

    fun convert2Entity(source: List<PolicyInsurant>?): List<PolicyInsurantEntity>?
}