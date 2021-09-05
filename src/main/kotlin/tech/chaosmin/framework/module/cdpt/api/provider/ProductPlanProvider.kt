/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.AbstractAPI
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.api.ProductPlanAPI
import tech.chaosmin.framework.module.cdpt.api.convert.ProductPlanConvert
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductPlanEx
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanEntity
import tech.chaosmin.framework.module.cdpt.entity.request.ProductPlanReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductPlanResp
import tech.chaosmin.framework.module.cdpt.logic.handler.ProductPlanModifyHandler
import tech.chaosmin.framework.module.cdpt.logic.interrogator.ProductPlanInterrogator
import tech.chaosmin.framework.utils.SecurityUtil

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class ProductPlanProvider(
    private val productPlanInterrogator: ProductPlanInterrogator,
    private val productPlanModifyHandler: ProductPlanModifyHandler
) : AbstractAPI<ProductPlanEx, ProductPlanEntity, ProductPlanReq, ProductPlanResp>(
    ProductPlanConvert.INSTANCE, productPlanInterrogator, productPlanModifyHandler
), ProductPlanAPI {

    override fun contractPage(id: Long): RestResult<IPage<ProductPlanResp?>> {
        val page = if (SecurityUtil.getUserDetails().isAdmin) {
            productPlanInterrogator.page(PageQuery.emptyQuery())
        } else productPlanInterrogator.contract(id)
        return RestResultExt.successRestResult(page.convert(ProductPlanConvert.INSTANCE::toResp))
    }
}