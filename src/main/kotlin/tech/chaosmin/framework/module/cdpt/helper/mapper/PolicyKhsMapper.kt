package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyKhs
import tech.chaosmin.framework.module.cdpt.entity.PolicyKhsEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface PolicyKhsMapper {
    companion object {
        val INSTANCE: PolicyKhsMapper = Mappers.getMapper(PolicyKhsMapper::class.java)
    }

    fun convert2DO(source: PolicyKhsEntity?): PolicyKhs?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: PolicyKhs?): PolicyKhsEntity?

    fun convert2Entity(source: List<PolicyKhs>?): List<PolicyKhsEntity>?
}