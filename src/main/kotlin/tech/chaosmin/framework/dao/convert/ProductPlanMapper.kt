package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.convert.base.BaseMapper
import tech.chaosmin.framework.dao.dataobject.ProductPlan
import tech.chaosmin.framework.domain.entity.ProductPlanEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper
interface ProductPlanMapper : BaseMapper<ProductPlanEntity, ProductPlan> {
    companion object {
        val INSTANCE: ProductPlanMapper = Mappers.getMapper(ProductPlanMapper::class.java)
    }
}