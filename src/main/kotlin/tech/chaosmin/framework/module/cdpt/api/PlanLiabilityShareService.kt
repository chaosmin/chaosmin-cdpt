package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseShareService
import tech.chaosmin.framework.module.cdpt.entity.request.PlanLiabilityReq
import tech.chaosmin.framework.module.cdpt.entity.response.PlanLiabilityResp

@Api(tags = ["计划责任操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/plan-liabilities")
interface PlanLiabilityShareService : BaseShareService<PlanLiabilityReq, PlanLiabilityResp>