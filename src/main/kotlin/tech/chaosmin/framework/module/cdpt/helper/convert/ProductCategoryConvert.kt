package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.cdpt.entity.ProductCategoryEntity
import tech.chaosmin.framework.module.cdpt.entity.request.ProductCategoryReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductCategoryResp

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