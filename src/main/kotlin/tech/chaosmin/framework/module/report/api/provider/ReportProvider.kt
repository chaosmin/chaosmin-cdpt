/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * ReportProvider.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.report.api.provider

import cn.hutool.core.date.DateUtil
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.report.api.ReportAPI
import tech.chaosmin.framework.module.report.entity.ReportEntity
import tech.chaosmin.framework.module.report.entity.condition.PolicyLatitude
import tech.chaosmin.framework.module.report.entity.condition.TwoLatitude
import tech.chaosmin.framework.module.report.entity.condition.UserLatitude
import tech.chaosmin.framework.module.report.entity.enums.ReportTypeEnum
import tech.chaosmin.framework.module.report.entity.enums.TimeTypeEnum
import tech.chaosmin.framework.module.report.entity.request.TwoLatitudeReq
import tech.chaosmin.framework.module.report.entity.result.BillingListResult
import tech.chaosmin.framework.module.report.entity.result.PersonalComsSetResult
import tech.chaosmin.framework.module.report.logic.handler.BillingListHandler
import tech.chaosmin.framework.module.report.logic.handler.PersonalComsSetHandler
import tech.chaosmin.framework.utils.JsonUtil
import java.util.*
import javax.servlet.http.HttpServletResponse

/**
 * @author Romani min
 * @since 2021/9/10 16:50
 */
@RestController
open class ReportProvider(
    private val billingListHandler: BillingListHandler,
    private val personalComsSetHandler: PersonalComsSetHandler
) : ReportAPI {
    override fun billingListReport(req: TwoLatitudeReq<PolicyLatitude, UserLatitude>): RestResult<BillingListResult> {
        val reportType = ReportTypeEnum.BILLING_LIST
        val reportEntity = convertToEntity<BillingListResult>(reportType, req)
        val restResult = billingListHandler.operate(reportEntity)
        return restResult.convert { it.result ?: BillingListResult() }
    }

    override fun billingListReport(req: TwoLatitudeReq<PolicyLatitude, UserLatitude>, response: HttpServletResponse) {
        val reportType = ReportTypeEnum.BILLING_LIST
        val reportEntity = convertToEntity<BillingListResult>(reportType, req)
        billingListHandler.download(reportEntity, response)
    }

    override fun personalComsSetReport(req: TwoLatitudeReq<PolicyLatitude, UserLatitude>): RestResult<PersonalComsSetResult> {
        val reportType = ReportTypeEnum.PERSONAL_COMMISSION_SETTLEMENT
        val reportEntity = convertToEntity<PersonalComsSetResult>(reportType, req)
        val restResult = personalComsSetHandler.operate(reportEntity)
        return restResult.convert { it.result ?: PersonalComsSetResult() }
    }

    override fun personalComsSetReport(req: TwoLatitudeReq<PolicyLatitude, UserLatitude>, response: HttpServletResponse) {
        val reportType = ReportTypeEnum.PERSONAL_COMMISSION_SETTLEMENT
        val reportEntity = convertToEntity<PersonalComsSetResult>(reportType, req)
        personalComsSetHandler.download(reportEntity, response)
    }

    private fun <R> convertToEntity(reportType: ReportTypeEnum, req: TwoLatitudeReq<PolicyLatitude, UserLatitude>)
            : ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, R> {
        val reportEntity = ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, R>(reportType)
        val policy = JsonUtil.readTree(req.first.toString())
        val user = JsonUtil.readTree(req.second.toString())
        reportEntity.condition = Collections.singletonList(
            TwoLatitude(
                PolicyLatitude().apply {
                    this.timeType = TimeTypeEnum.valueOf(policy.get("timeType").textValue())
                    this.startTime = DateUtil.parse(policy.get("startTime").textValue(), "yyyy-MM-dd")
                    this.endTime = DateUtil.parse(policy.get("endTime").textValue(), "yyyy-MM-dd")
                },
                UserLatitude().apply {
                    this.userId = user.get("userId").longValue()
                }
            )
        )
        return reportEntity
    }
}