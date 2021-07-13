package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Partner
import tech.chaosmin.framework.module.cdpt.entity.PartnerEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper
interface PartnerMapper {
    companion object {
        val INSTANCE: PartnerMapper = Mappers.getMapper(PartnerMapper::class.java)
    }

    fun convert2DO(source: PartnerEntity?): Partner?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: Partner?): PartnerEntity?

    fun convert2Entity(source: List<Partner>?): List<PartnerEntity>?
}