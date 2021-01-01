package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseShareService
import tech.chaosmin.framework.module.cdpt.entity.request.PlanRateTableReq
import tech.chaosmin.framework.module.cdpt.entity.response.PlanRateTableResp

@Api(tags = ["计划费率表操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/product-plan-rate-table")
interface PlanRateTableShareService : BaseShareService<PlanRateTableReq, PlanRateTableResp>