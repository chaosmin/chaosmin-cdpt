package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.cdpt.entity.ProductEntity
import tech.chaosmin.framework.module.cdpt.entity.request.ProductReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductResp

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper
interface ProductConvert : BaseConvert<ProductEntity, ProductReq, ProductResp> {
    companion object {
        val INSTANCE: ProductConvert = Mappers.getMapper(ProductConvert::class.java)
    }
}