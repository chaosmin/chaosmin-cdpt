package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.base.BaseShareService
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.cdpt.entity.request.OrderReq
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.entity.response.OrderResp

@Api(tags = ["订单操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/orders")
interface OrderShareService : BaseShareService<OrderReq, OrderResp> {
    @GetMapping("/{orderNo}/draft-box")
    fun getDraft(@PathVariable("orderNo") orderNo: String): RestResult<PolicyIssueReq>

    @PostMapping("/{orderNo}/draft-box")
    fun saveDraft(@PathVariable("orderNo") orderNo: String, @RequestBody req: PolicyIssueReq): RestResult<String>
}