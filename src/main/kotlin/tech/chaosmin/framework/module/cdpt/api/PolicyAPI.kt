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
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseAPI
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyResp

@Api(tags = ["保单操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/policies")
interface PolicyAPI : BaseAPI<PolicyReq, PolicyResp> {
    @GetMapping("/{orderNo}/detail")
    // TODO 迁移至order api内
    @ApiOperation(value = "获取指定订单的明细内容")
    fun policyDetail(@PathVariable("orderNo") orderNo: String): RestResult<PolicyIssueReq>
}