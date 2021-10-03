/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api.provider

import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.AbstractAPI
import tech.chaosmin.framework.module.cdpt.api.ProductPlanLibAPI
import tech.chaosmin.framework.module.cdpt.api.convert.ProductPlanLibConvert
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductPlanLib
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanLibEntity
import tech.chaosmin.framework.module.cdpt.entity.request.ProductPlanLibReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductPlanLibResp
import tech.chaosmin.framework.module.cdpt.logic.handler.ProductPlanLibModifyHandler
import tech.chaosmin.framework.module.cdpt.logic.interrogator.ProductPlanLibInterrogator

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class ProductPlanLibProvider(
    private val productPlanLibInterrogator: ProductPlanLibInterrogator,
    private val productPlanLibModifyHandler: ProductPlanLibModifyHandler
) : AbstractAPI<ProductPlanLib, ProductPlanLibEntity, ProductPlanLibReq, ProductPlanLibResp>(
    ProductPlanLibConvert.INSTANCE, productPlanLibInterrogator, productPlanLibModifyHandler
), ProductPlanLibAPI