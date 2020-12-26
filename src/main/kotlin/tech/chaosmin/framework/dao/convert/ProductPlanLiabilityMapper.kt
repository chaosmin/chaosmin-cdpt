package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.convert.base.BaseMapper
import tech.chaosmin.framework.dao.convert.base.KeyValueEnumMapper
import tech.chaosmin.framework.dao.dataobject.ProductPlanLiability
import tech.chaosmin.framework.domain.entity.ProductPlanLiabilityEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface ProductPlanLiabilityMapper : BaseMapper<ProductPlanLiabilityEntity, ProductPlanLiability> {
    companion object {
        val INSTANCE: ProductPlanLiabilityMapper = Mappers.getMapper(ProductPlanLiabilityMapper::class.java)
    }
}