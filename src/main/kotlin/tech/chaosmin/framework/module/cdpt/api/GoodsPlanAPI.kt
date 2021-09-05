/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * GoodsPlanAPI.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseAPI
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.cdpt.entity.request.GoodsPlanReq
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsCategoryResp
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsPlanResp
import javax.servlet.http.HttpServletRequest

@Api(tags = ["个人产品计划操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/goods-plans")
interface GoodsPlanAPI : BaseAPI<GoodsPlanReq, GoodsPlanResp> {
    @GetMapping("/users/{id}")
    @ApiOperation(value = "查询指定用户所有已授权产品")
    fun user(@PathVariable("id") userId: Long, request: HttpServletRequest): RestResult<List<GoodsPlanResp>>

    // TODO 后期考虑移除该接口
    @GetMapping("/users/{id}/categories")
    @ApiOperation(value = "查询指定用户所有已授权产品的产品分类")
    fun userCategories(@PathVariable("id") userId: Long): RestResult<List<GoodsCategoryResp>>
}