package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Partner
import tech.chaosmin.framework.module.cdpt.entity.PartnerEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper
interface PartnerMapper : BaseMapper<PartnerEntity, Partner> {
    companion object {
        val INSTANCE: PartnerMapper = Mappers.getMapper(PartnerMapper::class.java)
    }
}