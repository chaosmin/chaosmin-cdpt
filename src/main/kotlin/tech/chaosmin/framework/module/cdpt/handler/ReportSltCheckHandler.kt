package tech.chaosmin.framework.module.cdpt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.PolicyStatusEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.entity.report.SltCheckEntity
import tech.chaosmin.framework.module.cdpt.entity.report.SltCheckReportEntity
import tech.chaosmin.framework.module.cdpt.service.inner.GoodsPlanService
import tech.chaosmin.framework.module.cdpt.service.inner.PolicyService

/**
 * @author Romani min
 * @since 2021/6/29 17:01
 */
@Component
class ReportSltCheckHandler(
    private val policyService: PolicyService,
    private val goodsPlanService: GoodsPlanService
) : AbstractTemplateOperate<SltCheckReportEntity, SltCheckReportEntity>() {
    override fun validation(arg: SltCheckReportEntity, result: RestResult<SltCheckReportEntity>) {
        if (arg.statisticsStartTime == null || arg.statisticsEndTime == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "statisticsTime")
        }
    }

    override fun processor(arg: SltCheckReportEntity, result: RestResult<SltCheckReportEntity>): RestResult<SltCheckReportEntity> {
        val ew = Wrappers.query<Policy>()
            .eq("status", PolicyStatusEnum.SUCCESS.getCode())
            .between("create_time", arg.statisticsStartTime, arg.statisticsEndTime)
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
        return result.success(arg)
    }
}