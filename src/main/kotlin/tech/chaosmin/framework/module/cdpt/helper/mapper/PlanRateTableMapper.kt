package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanRateTable
import tech.chaosmin.framework.module.cdpt.entity.PlanRateTableEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface PlanRateTableMapper {
    companion object {
        val INSTANCE: PlanRateTableMapper = Mappers.getMapper(PlanRateTableMapper::class.java)
    }

    fun convert2DO(source: PlanRateTableEntity?): PlanRateTable?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: PlanRateTable?): PlanRateTableEntity?

    fun convert2Entity(source: List<PlanRateTable>?): List<PlanRateTableEntity>?
}