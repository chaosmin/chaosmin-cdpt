package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseMapper
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.PolicyExt
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface PolicyMapper : BaseMapper<PolicyEntity, Policy> {
    companion object {
        val INSTANCE: PolicyMapper = Mappers.getMapper(PolicyMapper::class.java)
    }

    @Mappings(
        value = [
            Mapping(target = "holder.name", source = "holderName"),
            Mapping(target = "goodsPlan.partnerName", source = "partnerName"),
            Mapping(target = "insuredSize", source = "insuredSize")
        ]
    )
    fun convert2Entity(source: PolicyExt): PolicyEntity
}