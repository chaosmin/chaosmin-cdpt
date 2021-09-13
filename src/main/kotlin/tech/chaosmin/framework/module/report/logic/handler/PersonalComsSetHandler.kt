/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PersonalCommissionSettlementHandler.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.report.logic.handler

import cn.hutool.poi.excel.ExcelUtil
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.PolicyEx
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.logic.interrogator.GoodsPlanInterrogator
import tech.chaosmin.framework.module.cdpt.logic.interrogator.PolicyInterrogator
import tech.chaosmin.framework.module.report.entity.ReportEntity
import tech.chaosmin.framework.module.report.entity.condition.PolicyLatitude
import tech.chaosmin.framework.module.report.entity.condition.TwoLatitude
import tech.chaosmin.framework.module.report.entity.condition.UserLatitude
import tech.chaosmin.framework.module.report.entity.enums.TimeTypeEnum
import tech.chaosmin.framework.module.report.entity.result.PersonalComsSetResult
import tech.chaosmin.framework.utils.HttpUtil
import tech.chaosmin.framework.utils.PageQueryUtil
import java.math.RoundingMode
import java.net.URLEncoder
import java.util.*
import javax.servlet.http.HttpServletResponse

/**
 * @author Romani min
 * @since 2021/9/10 16:45
 */
@Component
class PersonalComsSetHandler(
    private val policyInterrogator: PolicyInterrogator,
    private val goodsPlanInterrogator: GoodsPlanInterrogator
) : AbstractTemplateOperate<ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, PersonalComsSetResult>,
        ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, PersonalComsSetResult>>() {
    private val logger = LoggerFactory.getLogger(PersonalComsSetHandler::class.java)

    override fun validation(
        arg: ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, PersonalComsSetResult>,
        res: RestResult<ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, PersonalComsSetResult>>
    ) {
        if (arg.type == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL, "报表类型[type]")
        }
        if (arg.condition == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL, "报表数据范围[condition]")
        }
    }

    override fun processor(
        arg: ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, PersonalComsSetResult>,
        res: RestResult<ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, PersonalComsSetResult>>
    ): RestResult<ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, PersonalComsSetResult>> {
        val policyList = getData(arg.condition!!)
        logger.info("Total data list size: ${policyList.size}")
        if (policyList.isNotEmpty()) {
            val personalComsSetResult = PersonalComsSetResult()
            personalComsSetResult.detail = policyList.groupBy { it.goodsPlanId }.map { (goodsPlanId, list) ->
                val goodsPlan = goodsPlanInterrogator.getOne(goodsPlanId!!)
                PersonalComsSetResult.Detail().apply {
                    this.goodsPlanId = goodsPlanId
                    this.userId = goodsPlan.userId
                    this.userName = goodsPlan.userName
                    this.goodsPlanName = "[${goodsPlan.productName}]-${goodsPlan.productPlanName}[${goodsPlan.primaryCoverage}]"
                    this.partnerName = goodsPlan.partnerName
                    this.discount = "${goodsPlan.maxComsRatio?.minus(goodsPlan.comsRatio ?: 0.0)}%"
                    this.agreementComsRatio = "${goodsPlan.maxComsRatio}%"
                    this.settlementComsRatio = "${goodsPlan.comsRatio}%"
                    this.originalPremium = list.sumByDouble { it.totalPremium ?: 0.0 }
                    this.settlementPremium = list.sumByDouble { it.actualPremium ?: 0.0 }
                    this.settlementComs = this.originalPremium!! - this.settlementPremium!!
                }
            }
            val originalPrice = personalComsSetResult.detail!!.sumByDouble { it.originalPremium ?: 0.0 }
            personalComsSetResult.totalOriginalPremium = originalPrice.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
            val settlementComs = personalComsSetResult.detail!!.sumByDouble { it.settlementComs ?: 0.0 }
            personalComsSetResult.totalSettlementComs = settlementComs.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
            arg.result = personalComsSetResult
        }
        return res.success(arg)
    }

    fun download(arg: ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, PersonalComsSetResult>, response: HttpServletResponse) {
        val result = operate(arg).data?.result ?: throw FrameworkException(ErrorCodeEnum.DATA_ERROR, "无法获取报表数据")
        ExcelUtil.getWriter().use { writer ->
            writer.writeHeadRow(result.header)
            result.detail?.forEach {
                writer.writeRow(
                    listOf(
                        it.goodsPlanName, it.partnerName,
                        it.originalPremium, it.settlementPremium,
                        it.discount,
                        it.agreementComsRatio, it.settlementComsRatio,
                        it.settlementComs
                    )
                )
            }
            writer.writeRow(listOf("合计", "", result.totalOriginalPremium, "", "", "", "", result.totalSettlementComs))
            writer.autoSizeColumnAll()
            val fileName = URLEncoder.encode("${result.detail?.first()?.userName}个人佣金结算表.xls", "utf-8")
            HttpUtil.writeExcel(response, fileName, writer)
        }
    }

    private fun getData(condition: List<TwoLatitude<PolicyLatitude, UserLatitude>>): List<PolicyEntity> {
        val wa = Wrappers.query<PolicyEx>()
        condition.forEach { c ->
            val status = c.first?.mapNotNull { it.status?.getCode() }
            if (status?.isNotEmpty() == true) wa.`in`("policy.status", status)
            val payType = c.first?.mapNotNull { it.payType?.getCode() }
            if (payType?.isNotEmpty() == true) wa.`in`("policy.pay_type", payType)
            val payStatus = c.first?.mapNotNull { it.payStatus?.getCode() }
            if (payStatus?.isNotEmpty() == true) wa.`in`("policy.pay_status", payStatus)
            c.first?.forEach { p ->
                when (p.timeType) {
                    TimeTypeEnum.ISSUE_TIME -> wa.between("policy.issue_time", p.startTime ?: Date(), p.endTime ?: Date())
                    TimeTypeEnum.EFFECTIVE_TIME -> wa.between("policy.effective_time", p.startTime ?: Date(), p.endTime ?: Date())
                    TimeTypeEnum.EXPIRY_TIME -> wa.between("policy.expiry_time", p.startTime ?: Date(), p.endTime ?: Date())
                    else -> logger.error("Unsupported policy time type: ${p.timeType?.name}")
                }
            }
            val userIds = c.second?.mapNotNull { it.userId }
            if (userIds?.isNotEmpty() == true) wa.`in`("policy.user_id", userIds)
        }
        return PageQueryUtil.queryAll(policyInterrogator, wa)
    }
}