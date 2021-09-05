/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.TimeTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.domain.service.GoodsPlanService
import tech.chaosmin.framework.module.cdpt.domain.service.PolicyService
import tech.chaosmin.framework.module.cdpt.entity.enums.PayTypeEnum
import tech.chaosmin.framework.module.cdpt.entity.enums.PolicyStatusEnum
import tech.chaosmin.framework.module.cdpt.entity.report.SltCheckEntity
import tech.chaosmin.framework.module.cdpt.entity.report.SltCheckReportEntity

/**
 * @author Romani min
 * @since 2021/6/29 17:01
 */
@Component
class ReportSltCheckHandler(
    private val policyService: PolicyService,
    private val goodsPlanService: GoodsPlanService
) : AbstractTemplateOperate<SltCheckReportEntity, SltCheckReportEntity>() {
    override fun validation(arg: SltCheckReportEntity, res: RestResult<SltCheckReportEntity>) {
        if (arg.timeType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "时间范围类型")
        }
        if (arg.startTime == null || arg.endTime == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "统计时间")
        }
    }

    override fun processor(arg: SltCheckReportEntity, res: RestResult<SltCheckReportEntity>): RestResult<SltCheckReportEntity> {
        var ew = Wrappers.query<Policy>().eq("status", PolicyStatusEnum.INSURED.getCode())
            .eq("pay_type", PayTypeEnum.OFFLINE.getCode())
        if (arg.userId != null) ew = ew.eq("user_id", arg.userId)
        when (arg.timeType) {
            TimeTypeEnum.EFFECTIVE_TIME -> ew = ew.between("effective_time", arg.startTime, arg.endTime)
            TimeTypeEnum.EXPIRY_TIME -> ew = ew.between("expiry_time", arg.startTime, arg.endTime)
            TimeTypeEnum.ISSUE_TIME -> ew = ew.between("create_time", arg.startTime, arg.endTime)
        }
        val policyList = policyService.list(ew)
        if (policyList.isNotEmpty()) {
            arg.detail = policyList.groupBy { it.goodsPlanId }.flatMap { (goodsPlanId, list) ->
                val goodsPlan = goodsPlanService.getById(goodsPlanId)
                list.map {
                    SltCheckEntity().apply {
                        this.orderNo = it.orderNo
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
                    }
                }
            }
            arg.totalInsuredSize = arg.detail?.sumBy { it.insuredSize ?: 0 }
            arg.totalPremium = arg.detail?.sumByDouble { it.totalPremium ?: 0.0 }
            arg.actualPremium = arg.detail?.sumByDouble { it.actualPremium ?: 0.0 }
            arg.doPartner()
        }
        return res.success(arg)
    }
}