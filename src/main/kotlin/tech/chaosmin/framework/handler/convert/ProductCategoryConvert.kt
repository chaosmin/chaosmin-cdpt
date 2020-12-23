package tech.chaosmin.framework.handler.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.domain.entity.ProductCategoryEntity
import tech.chaosmin.framework.domain.request.ProductCategoryReq
import tech.chaosmin.framework.domain.response.ProductCategoryResp
import tech.chaosmin.framework.handler.convert.base.BaseConvert

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper
interface ProductCategoryConvert : BaseConvert<ProductCategoryEntity, ProductCategoryReq, ProductCategoryResp> {
    companion object {
        val INSTANCE: ProductCategoryConvert = Mappers.getMapper(ProductCategoryConvert::class.java)
    }
}