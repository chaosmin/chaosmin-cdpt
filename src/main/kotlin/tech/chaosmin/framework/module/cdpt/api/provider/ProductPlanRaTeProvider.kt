package tech.chaosmin.framework.module.cdpt.api.provider

import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.AbstractAPI
import tech.chaosmin.framework.module.cdpt.api.ProductPlanRaTeAPI
import tech.chaosmin.framework.module.cdpt.api.convert.ProductPlanRaTeConvert
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductPlanRaTe
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanRaTeEntity
import tech.chaosmin.framework.module.cdpt.entity.request.ProductPlanRaTeReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductPlanRaTeResp
import tech.chaosmin.framework.module.cdpt.logic.handler.ProductPlanRaTeModifyHandler
import tech.chaosmin.framework.module.cdpt.logic.interrogator.ProductPlanRaTeInterrogator

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class ProductPlanRaTeProvider(
    private val productPlanRaTeInterrogator: ProductPlanRaTeInterrogator,
    private val productPlanRaTeModifyHandler: ProductPlanRaTeModifyHandler
) : AbstractAPI<ProductPlanRaTe, ProductPlanRaTeEntity, ProductPlanRaTeReq, ProductPlanRaTeResp>(
    ProductPlanRaTeConvert.INSTANCE, productPlanRaTeInterrogator, productPlanRaTeModifyHandler
), ProductPlanRaTeAPI