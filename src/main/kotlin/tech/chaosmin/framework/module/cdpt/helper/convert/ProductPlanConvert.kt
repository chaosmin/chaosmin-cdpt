package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanEntity
import tech.chaosmin.framework.module.cdpt.entity.request.ProductPlanReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductPlanResp

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