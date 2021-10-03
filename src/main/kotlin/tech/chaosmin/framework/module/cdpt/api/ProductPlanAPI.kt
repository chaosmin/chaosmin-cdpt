/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseAPI
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.cdpt.entity.request.ProductPlanReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductPlanResp

@Api(tags = ["产品计划操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/product-plans")
interface ProductPlanAPI : BaseAPI<ProductPlanReq, ProductPlanResp> {
    // TODO 这是什么接口
    @GetMapping("/users/{id}/contract")
    @ApiOperation(value = "")
    fun contractPage(@PathVariable("id") id: Long): RestResult<IPage<ProductPlanResp?>>
}