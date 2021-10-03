/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * InsureAPI.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyResp

/**
 * @author Romani min
 * @since 2021/1/15 16:35
 */
@Api(tags = ["投保操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/insure")
interface InsureAPI {
    @GetMapping("/biz-no")
    @ApiOperation(value = "生成随机订单号(order-no)")
    fun getBizNo(): RestResult<String>

    @PostMapping
    @ApiOperation(value = "投保单提交")
    fun insurance(@RequestBody req: PolicyIssueReq): RestResult<PolicyResp>
}