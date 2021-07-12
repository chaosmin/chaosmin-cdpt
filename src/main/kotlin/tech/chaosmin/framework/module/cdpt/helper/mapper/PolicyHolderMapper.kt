package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyHolder
import tech.chaosmin.framework.module.cdpt.entity.PolicyHolderEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface PolicyHolderMapper {
    companion object {
        val INSTANCE: PolicyHolderMapper = Mappers.getMapper(PolicyHolderMapper::class.java)
    }

    fun convert2DO(source: PolicyHolderEntity?): PolicyHolder?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: PolicyHolder?): PolicyHolderEntity?

    fun convert2Entity(source: List<PolicyHolder>?): List<PolicyHolderEntity>?
}