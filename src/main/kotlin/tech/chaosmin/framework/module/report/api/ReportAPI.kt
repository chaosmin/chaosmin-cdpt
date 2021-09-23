/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * ReportAPI.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.report.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.report.entity.condition.PolicyLatitude
import tech.chaosmin.framework.module.report.entity.condition.UserLatitude
import tech.chaosmin.framework.module.report.entity.request.TwoLatitudeReq
import tech.chaosmin.framework.module.report.entity.result.BillingListResult
import tech.chaosmin.framework.module.report.entity.result.BusinessResult
import tech.chaosmin.framework.module.report.entity.result.PersonalComsSetResult
import javax.servlet.http.HttpServletResponse

/**
 * @author Romani min
 * @since 2021/9/10 16:41
 */
@Api(tags = ["报表操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/reports")
interface ReportAPI {
    @GetMapping("/billing-list")
    fun billingListReport(req: TwoLatitudeReq<PolicyLatitude, UserLatitude>): RestResult<BillingListResult>

    @PostMapping("/billing-list")
    fun billingListReport(req: TwoLatitudeReq<PolicyLatitude, UserLatitude>, response: HttpServletResponse)

    @GetMapping("/personal-commission-settlement")
    fun personalComsSetReport(req: TwoLatitudeReq<PolicyLatitude, UserLatitude>): RestResult<PersonalComsSetResult>

    @PostMapping("/personal-commission-settlement")
    fun personalComsSetReport(req: TwoLatitudeReq<PolicyLatitude, UserLatitude>, response: HttpServletResponse)

    @GetMapping("/business")
    fun businessReport(req: TwoLatitudeReq<PolicyLatitude, UserLatitude>): RestResult<BusinessResult>

    @PostMapping("/business")
    fun businessReport(req: TwoLatitudeReq<PolicyLatitude, UserLatitude>, response: HttpServletResponse)
}