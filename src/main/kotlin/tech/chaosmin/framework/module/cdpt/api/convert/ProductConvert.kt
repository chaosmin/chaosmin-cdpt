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
import tech.chaosmin.framework.module.cdpt.entity.ProductEntity
import tech.chaosmin.framework.module.cdpt.entity.request.ProductReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductResp

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper
interface ProductConvert : IConvert<ProductEntity, ProductReq, ProductResp> {
    companion object {
        val INSTANCE: ProductConvert = Mappers.getMapper(ProductConvert::class.java)
    }
}