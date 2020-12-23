package tech.chaosmin.framework.handler.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.domain.entity.ProductEntity
import tech.chaosmin.framework.domain.request.ProductReq
import tech.chaosmin.framework.domain.response.ProductResp
import tech.chaosmin.framework.handler.convert.base.BaseConvert

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