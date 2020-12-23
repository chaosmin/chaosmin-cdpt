package tech.chaosmin.framework.handler.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.domain.entity.ProductPlanEntity
import tech.chaosmin.framework.domain.request.ProductPlanReq
import tech.chaosmin.framework.domain.response.ProductPlanResp
import tech.chaosmin.framework.handler.convert.base.BaseConvert

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper
interface ProductPlanConvert : BaseConvert<ProductPlanEntity, ProductPlanReq, ProductPlanResp> {
    companion object {
        val INSTANCE: ProductPlanConvert = Mappers.getMapper(ProductPlanConvert::class.java)
    }
}