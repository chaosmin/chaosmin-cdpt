package tech.chaosmin.framework.handler.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.domain.entity.ProductPlanLiabilityEntity
import tech.chaosmin.framework.domain.request.ProductPlanLiabilityReq
import tech.chaosmin.framework.domain.response.ProductPlanLiabilityResp
import tech.chaosmin.framework.handler.convert.base.BaseConvert

/**
 * @author Romani min
 * @since 2020/12/23 21:30
 */
@Mapper
interface ProductPlanLiabilityConvert : BaseConvert<ProductPlanLiabilityEntity, ProductPlanLiabilityReq, ProductPlanLiabilityResp> {
    companion object {
        val INSTANCE: ProductPlanLiabilityConvert = Mappers.getMapper(ProductPlanLiabilityConvert::class.java)
    }
}