package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanLiability
import tech.chaosmin.framework.module.cdpt.entity.PlanLiabilityEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface PlanLiabilityMapper {
    companion object {
        val INSTANCE: PlanLiabilityMapper = Mappers.getMapper(PlanLiabilityMapper::class.java)
    }

    fun convert2DO(source: PlanLiabilityEntity?): PlanLiability?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: PlanLiability?): PlanLiabilityEntity?

    fun convert2Entity(source: List<PlanLiability>?): List<PlanLiabilityEntity>?
}