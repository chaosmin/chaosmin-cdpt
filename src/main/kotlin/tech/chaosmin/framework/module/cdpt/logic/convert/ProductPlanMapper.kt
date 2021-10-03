/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * ProductPlanMapper.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.IMapper
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductPlan
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductPlanEx
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanEntity

@Mapper(uses = [KeyValueEnumMapper::class])
interface ProductPlanMapper : IMapper<ProductPlanEntity, ProductPlan> {
    companion object {
        val INSTANCE: ProductPlanMapper = Mappers.getMapper(ProductPlanMapper::class.java)
    }

    @Mappings(
        Mapping(target = "IComsRatio", source = "comsRatio"),
        Mapping(target = "modifyType", ignore = true)
    )
    fun exToEn(source: ProductPlanEx?): ProductPlanEntity?
}