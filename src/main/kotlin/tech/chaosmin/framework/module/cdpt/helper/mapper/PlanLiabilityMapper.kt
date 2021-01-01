package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseMapper
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanLiability
import tech.chaosmin.framework.module.cdpt.entity.PlanLiabilityEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface PlanLiabilityMapper : BaseMapper<PlanLiabilityEntity, PlanLiability> {
    companion object {
        val INSTANCE: PlanLiabilityMapper = Mappers.getMapper(PlanLiabilityMapper::class.java)
    }
}