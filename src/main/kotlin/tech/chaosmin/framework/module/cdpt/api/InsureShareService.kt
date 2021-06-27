package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
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
interface InsureShareService {
    @GetMapping("/biz-no")
    fun getBizNo(): RestResult<String>

    @PostMapping
    fun insurance(@RequestBody req: PolicyIssueReq): RestResult<PolicyResp>
}