/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * BusinessHandler.kt
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
import tech.chaosmin.framework.module.report.entity.result.BusinessResult
import tech.chaosmin.framework.utils.HttpUtil
import tech.chaosmin.framework.utils.PageQueryUtil
import java.net.URLEncoder
import java.util.*
import javax.servlet.http.HttpServletResponse

/**
 * @author Romani min
 * @since 2021/9/23 16:31
 */
@Component
class BusinessHandler(
    private val policyInterrogator: PolicyInterrogator,
    private val goodsPlanInterrogator: GoodsPlanInterrogator
) : AbstractTemplateOperate<ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BusinessResult>, ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BusinessResult>>() {
    private val logger = LoggerFactory.getLogger(BusinessHandler::class.java)

    override fun validation(
        arg: ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BusinessResult>,
        res: RestResult<ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BusinessResult>>
    ) {
        if (arg.condition == null || arg.condition?.firstOrNull() == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL, "报表时间范围[startTime,endTime]")
        }
    }

    override fun processor(
        arg: ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BusinessResult>,
        res: RestResult<ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BusinessResult>>
    ): RestResult<ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BusinessResult>> {
        val policyList = getData(arg.condition!!)
        logger.info("Total data list size: ${policyList.size}")
        if (policyList.isNotEmpty()) {
            val businessResult = BusinessResult()

            businessResult.detail = policyList.groupBy { it.goodsPlanId }.flatMap { (goodsPlanId, list) ->
                val goodsPlan = goodsPlanInterrogator.getOne(goodsPlanId!!)
                list.map {
                    BusinessResult.Detail().apply {
                        this.orderNo = it.orderNo
                        this.policyNo = it.policyNo
                        this.issueTime = it.issueTime
                        this.effectiveTime = it.effectiveTime
                        this.expiryTime = it.expiryTime
                        this.partnerName = goodsPlan.partnerName
                        this.productName = "[${goodsPlan.productName}]-${goodsPlan.productPlanName}[${goodsPlan.primaryCoverage}]"
                        this.issuerName = it.issuerName
                        this.premium = it.totalPremium
                        this.comsRatio = "${goodsPlan.comsRatio}%"
                        this.coms = it.totalPremium?.minus(it.actualPremium ?: 0.0)
                        this.status = it.status?.getDesc()
                        this.payType = it.payType?.getDesc()
                    }
                }
            }
            arg.result = businessResult
        }
        return res.success(arg)
    }

    fun download(arg: ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BusinessResult>, response: HttpServletResponse) {
        val result = operate(arg).data?.result ?: throw FrameworkException(ErrorCodeEnum.DATA_ERROR, "无法获取报表数据")
        ExcelUtil.getWriter().use { writer ->
            writer.writeHeadRow(result.header)
            result.detail?.forEach {
                writer.writeRow(
                    listOf(
                        it.orderNo, it.policyNo,
                        it.issueTime, it.effectiveTime, it.expiryTime,
                        it.partnerName, it.productName, it.issuerName,
                        it.premium, it.comsRatio, it.coms, it.status, it.payType
                    )
                )
            }
            writer.autoSizeColumnAll()
            val fileName = URLEncoder.encode("业管系统模板.xls", "utf-8")
            HttpUtil.writeExcel(response, fileName, writer)
        }
    }

    private fun getData(condition: List<TwoLatitude<PolicyLatitude, UserLatitude>>): List<PolicyEntity> {
        val wa = Wrappers.query<PolicyEx>()
        condition.forEach { c ->
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
        // TODO 优化在大数据量情况下的表现
        return PageQueryUtil.queryAll(policyInterrogator, wa)
    }
}