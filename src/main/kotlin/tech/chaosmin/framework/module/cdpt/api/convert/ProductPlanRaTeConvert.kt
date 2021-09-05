/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.IConvert
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanRaTeEntity
import tech.chaosmin.framework.module.cdpt.entity.request.ProductPlanRaTeReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductPlanRaTeResp

/**
 * @author Romani min
 * @since 2020/12/23 21:30
 */
@Mapper
interface ProductPlanRaTeConvert : IConvert<ProductPlanRaTeEntity, ProductPlanRaTeReq, ProductPlanRaTeResp> {
    companion object {
        val INSTANCE: ProductPlanRaTeConvert = Mappers.getMapper(ProductPlanRaTeConvert::class.java)
    }
}