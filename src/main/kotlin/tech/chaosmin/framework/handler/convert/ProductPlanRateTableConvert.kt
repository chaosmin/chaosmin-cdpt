package tech.chaosmin.framework.handler.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.domain.entity.ProductPlanRateTableEntity
import tech.chaosmin.framework.domain.request.ProductPlanRateTableReq
import tech.chaosmin.framework.domain.response.ProductPlanRateTableResp
import tech.chaosmin.framework.handler.convert.base.BaseConvert

/**
 * @author Romani min
 * @since 2020/12/23 21:30
 */
@Mapper
interface ProductPlanRateTableConvert : BaseConvert<ProductPlanRateTableEntity, ProductPlanRateTableReq, ProductPlanRateTableResp> {
    companion object {
        val INSTANCE: ProductPlanRateTableConvert = Mappers.getMapper(ProductPlanRateTableConvert::class.java)
    }
}