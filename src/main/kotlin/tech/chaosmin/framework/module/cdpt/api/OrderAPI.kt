/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.base.BaseAPI
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.cdpt.entity.request.OrderReq
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyTraceReq
import tech.chaosmin.framework.module.cdpt.entity.response.OrderResp

@Api(tags = ["订单操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/orders")
interface OrderAPI : BaseAPI<OrderReq, OrderResp> {
    @GetMapping("/{orderNo}/pay")
    @ApiOperation(value = "获取指定订单的支付链接")
    fun payment(@PathVariable("orderNo") orderNo: String): RestResult<String>

    @PostMapping("/{orderNo}/trace")
    fun saveOrderTrace(@PathVariable("orderNo") orderNo: String, @RequestBody req: PolicyTraceReq): RestResult<String>

    @GetMapping("/{orderNo}/draft-box")
    @ApiOperation(value = "读取指定订单的草稿箱")
    fun getDraft(@PathVariable("orderNo") orderNo: String): RestResult<PolicyIssueReq>

    @PostMapping("/{orderNo}/draft-box")
    @ApiOperation(value = "保存指定订单的草稿箱")
    fun saveDraft(@PathVariable("orderNo") orderNo: String, @RequestBody req: PolicyIssueReq): RestResult<String>
}