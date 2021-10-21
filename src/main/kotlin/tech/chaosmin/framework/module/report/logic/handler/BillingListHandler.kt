/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * BillingListHandler.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.report.logic.handler

import cn.hutool.core.date.DateUtil
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
import tech.chaosmin.framework.module.cdpt.entity.enums.PayTypeEnum
import tech.chaosmin.framework.module.cdpt.entity.enums.PolicyStatusEnum
import tech.chaosmin.framework.module.cdpt.logic.interrogator.GoodsPlanInterrogator
import tech.chaosmin.framework.module.cdpt.logic.interrogator.PolicyInterrogator
import tech.chaosmin.framework.module.report.entity.ReportEntity
import tech.chaosmin.framework.module.report.entity.condition.PolicyLatitude
import tech.chaosmin.framework.module.report.entity.condition.TwoLatitude
import tech.chaosmin.framework.module.report.entity.condition.UserLatitude
import tech.chaosmin.framework.module.report.entity.enums.TimeTypeEnum
import tech.chaosmin.framework.module.report.entity.result.BillingListResult
import tech.chaosmin.framework.utils.HttpUtil
import tech.chaosmin.framework.utils.NumberUtil
import tech.chaosmin.framework.utils.PageQueryUtil
import java.net.URLEncoder
import java.util.*
import javax.servlet.http.HttpServletResponse

/**
 * @author Romani min
 * @since 2021/9/10 15:50
 */
@Component
class BillingListHandler(
    private val policyInterrogator: PolicyInterrogator,
    private val goodsPlanInterrogator: GoodsPlanInterrogator
) : AbstractTemplateOperate<ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BillingListResult>,
        ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BillingListResult>>() {
    private val logger = LoggerFactory.getLogger(BillingListHandler::class.java)


    override fun validation(
        arg: ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BillingListResult>,
        res: RestResult<ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BillingListResult>>
    ) {
        if (arg.condition == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL, "报表数据范围[condition]")
        }
    }

    override fun processor(
        arg: ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BillingListResult>,
        res: RestResult<ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BillingListResult>>
    ): RestResult<ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BillingListResult>> {
        val policyList = getData(arg.condition!!)
        logger.info("Total data list size: ${policyList.size}")
        if (policyList.isNotEmpty()) {
            val billingListResult = BillingListResult()

            billingListResult.detail = policyList.groupBy { it.goodsPlanId }.flatMap { (goodsPlanId, list) ->
                val goodsPlan = goodsPlanInterrogator.getOne(goodsPlanId!!)
                list.map {
                    BillingListResult.Detail().apply {
                        this.orderNo = it.orderNo
                        this.policyNo = it.policyNo
                        this.goodsPlanName = "[${goodsPlan.productName}]-${goodsPlan.productPlanName}[${goodsPlan.primaryCoverage}]"
                        this.insuredSize = it.insuredSize
                        this.unitPremium = it.unitPremium
                        this.totalPremium = it.totalPremium
                        // 反算出来
                        this.actualUnitPremium = it.actualPremium?.div(it.insuredSize ?: 1)
                        this.actualPremium = it.actualPremium
                        this.effectiveTime = it.effectiveTime
                        this.expiryTime = it.expiryTime
                        this.issueTime = it.createTime
                        this.groupNo = it.groupNo
                        this.partnerName = goodsPlan.partnerName
                        this.issuer = goodsPlan.userName
                        this.address = it.travelDestination
                        this.payType = if (it.payType == PayTypeEnum.OFFLINE) "月结" else "微信支付"
                    }
                }
            }
            billingListResult.totalInsuredSize = billingListResult.detail?.sumBy { it.insuredSize ?: 0 }
            billingListResult.totalPremium = billingListResult.detail?.sumByDouble { it.totalPremium ?: 0.0 }
            billingListResult.actualPremium = billingListResult.detail?.sumByDouble { it.actualPremium ?: 0.0 }
            billingListResult.groupByPartner()
            arg.result = billingListResult
        }
        return res.success(arg)
    }

    fun download(arg: ReportEntity<TwoLatitude<PolicyLatitude, UserLatitude>, BillingListResult>, response: HttpServletResponse) {
        val result = operate(arg).data?.result ?: throw FrameworkException(ErrorCodeEnum.DATA_ERROR, "无法获取报表数据")
        val policyLatitude = arg.condition?.firstOrNull()?.first?.firstOrNull()
        val startTime = policyLatitude?.startTime ?: Date()
        val endTime = policyLatitude?.endTime ?: Date()
        val timeType = policyLatitude?.timeType ?: TimeTypeEnum.EFFECTIVE_TIME
        val startTimeStr = DateUtil.format(startTime, "yyyy年MM月dd日")
        val endTimeStr = DateUtil.format(endTime, "yyyy年MM月dd日")
        ExcelUtil.getWriter().use { writer ->
            writer.merge(15, "结算清单", true)
            writer.merge(15, "${timeType.getDesc()}${startTimeStr}-${endTimeStr}", false)
            writer.writeHeadRow(result.header)
            result.detail?.forEachIndexed { i, e ->
                writer.writeRow(
                    listOf(
                        (i + 1),
                        e.policyNo,
                        e.goodsPlanName,
                        e.insuredSize,
                        e.unitPremium,
                        e.totalPremium,
                        e.actualUnitPremium,
                        e.actualPremium,
                        DateUtil.format(e.effectiveTime, "yyyy-MM-dd"),
                        DateUtil.format(e.expiryTime, "yyyy-MM-dd"),
                        DateUtil.format(e.issueTime, "yyyy-MM-dd HH:mm"),
                        e.groupNo,
                        e.partnerName,
                        e.issuer,
                        e.address,
                        e.payType
                    )
                )
            }
            writer.writeRow(listOf("合计", "", "", result.totalInsuredSize, "", result.totalPremium, "", result.actualPremium))
            writer.merge(15, "金额大写: ${NumberUtil.toChinese(result.actualPremium?.toString() ?: "0")}", false)
            writer.writeRow(listOf(""))
            writer.merge(2, "保费分类统计", false)
            writer.writeRow(listOf("保险公司", "标准保费", "实收保费"))
            result.partnerList?.forEach { p ->
                writer.writeRow(listOf(p.partnerName, p.totalPremium, p.actualPremium))
            }
            // 2021-08-05 14:45:50 删除银行信息
//            writer.writeRow(listOf(""))
//            writer.merge(6, "银行账户信息", false)
//            writer.merge(6, "账户名称：中国太平洋财产保险股份有限公司北京分公司", false)
//            writer.merge(6, "开户银行：中国建设银行北京分行兴融支行", false)
//            writer.merge(6, "银行账号：11050167500000000875", false)
            writer.autoSizeColumnAll()
            val fileName = URLEncoder.encode("结算清单-${timeType.getDesc()}${startTimeStr}至${endTimeStr}.xls", "utf-8")
            HttpUtil.writeExcel(response, fileName, writer)
        }
    }

    private fun getData(condition: List<TwoLatitude<PolicyLatitude, UserLatitude>>): List<PolicyEntity> {
        val wa = Wrappers.query<PolicyEx>().eq("policy.status", PolicyStatusEnum.INSURED.getCode())
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