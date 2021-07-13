package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductPlan
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductPlanExt
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface ProductPlanMapper {
    companion object {
        val INSTANCE: ProductPlanMapper = Mappers.getMapper(ProductPlanMapper::class.java)
    }

    fun convert2DO(source: ProductPlanEntity?): ProductPlan?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: ProductPlan?): ProductPlanEntity?

    @Mapping(target = "IComsRatio", source = "comsRatio")
    fun convert2Entity(source: ProductPlanExt?): ProductPlanEntity?

    fun convert2Entity(source: List<ProductPlan>?): List<ProductPlanEntity>?
}