package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.convert.base.BaseMapper
import tech.chaosmin.framework.dao.dataobject.Partner
import tech.chaosmin.framework.domain.entity.PartnerEntity

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