package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseMapper
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyKhs
import tech.chaosmin.framework.module.cdpt.entity.PolicyKhsEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface PolicyKhsMapper : BaseMapper<PolicyKhsEntity, PolicyKhs> {
    companion object {
        val INSTANCE: PolicyKhsMapper = Mappers.getMapper(PolicyKhsMapper::class.java)
    }
}