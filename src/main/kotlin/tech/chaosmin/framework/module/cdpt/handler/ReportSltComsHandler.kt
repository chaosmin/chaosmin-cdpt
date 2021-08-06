package tech.chaosmin.framework.module.cdpt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.PolicyStatusEnum
import tech.chaosmin.framework.base.enums.TimeTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.entity.report.SltComsEntity
import tech.chaosmin.framework.module.cdpt.entity.report.SltComsReportEntity
import tech.chaosmin.framework.module.cdpt.service.inner.GoodsPlanService
import tech.chaosmin.framework.module.cdpt.service.inner.PolicyService

/**
 * @author Romani min
 * @since 2021/6/29 15:50
 */
@Component
class ReportSltComsHandler(
    private val policyService: PolicyService,
    private val goodsPlanService: GoodsPlanService
) : AbstractTemplateOperate<SltComsReportEntity, SltComsReportEntity>() {
    override fun validation(arg: SltComsReportEntity, result: RestResult<SltComsReportEntity>) {
        if (arg.timeType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "时间范围类型")
        }
        if (arg.startTime == null || arg.endTime == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "统计时间")
        }
        if (arg.userId == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "出单员")
        }
    }

    override fun processor(arg: SltComsReportEntity, result: RestResult<SltComsReportEntity>): RestResult<SltComsReportEntity> {
        arg.apply {
            this.detail = mutableListOf()
            this.totalOriginalPrice = 0.0
            this.totalSettlementPrice = 0.0
        }
        var ew = Wrappers.query<Policy>().eq("status", PolicyStatusEnum.SUCCESS.getCode()).eq("user_id", arg.userId)
        when (arg.timeType) {
            TimeTypeEnum.EFFECTIVE_TIME -> ew = ew.between("effective_time", arg.startTime, arg.endTime)
            TimeTypeEnum.EXPIRY_TIME -> ew = ew.between("expiry_time", arg.startTime, arg.endTime)
            TimeTypeEnum.ISSUE_TIME -> ew = ew.between("create_time", arg.startTime, arg.endTime)
        }
        val policyList = policyService.list(ew)
        if (policyList.isNotEmpty()) {
            arg.detail = policyList.groupBy { it.goodsPlanId }.map { (goodsPlanId, list) ->
                val goodsPlan = goodsPlanService.getById(goodsPlanId)
                SltComsEntity().apply {
                    this.goodsPlanId = goodsPlanId
                    this.userId = goodsPlan.userId
                    this.userName = goodsPlan.userName
                    this.goodsPlanName = "[${goodsPlan.productName}]-${goodsPlan.productPlanName}[${goodsPlan.primaryCoverage}]"
                    this.partnerName = goodsPlan.partnerName
                    this.discount = "${goodsPlan.maxComsRatio?.minus(goodsPlan.comsRatio ?: 0.0)}%"
                    this.agreementComsRatio = "${goodsPlan.maxComsRatio}%"
                    this.settlementComsRatio = "${goodsPlan.comsRatio}%"
                    this.originalPrice = list.sumByDouble { it.totalPremium ?: 0.0 }
                    this.settlementPrice = list.sumByDouble { it.actualPremium ?: 0.0 }
                    this.settlementComs = this.originalPrice!! - this.settlementPrice!!
                }
            }
            arg.totalOriginalPrice = arg.detail?.sumByDouble { it.originalPrice ?: 0.0 }
            arg.totalSettlementPrice = arg.detail?.sumByDouble { it.settlementComs ?: 0.0 }
        }
        return result.success(arg)
    }
}