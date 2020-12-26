package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.convert.base.BaseMapper
import tech.chaosmin.framework.dao.convert.base.KeyValueEnumMapper
import tech.chaosmin.framework.dao.dataobject.ProductPlanRateTable
import tech.chaosmin.framework.domain.entity.ProductPlanRateTableEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface ProductPlanRateTableMapper : BaseMapper<ProductPlanRateTableEntity, ProductPlanRateTable> {
    companion object {
        val INSTANCE: ProductPlanRateTableMapper = Mappers.getMapper(ProductPlanRateTableMapper::class.java)
    }
}