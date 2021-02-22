package tech.chaosmin.framework.module.cdpt.handler

import cn.hutool.core.date.DateUtil
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.handler.logic.PlanRateTableQueryLogic
import tech.chaosmin.framework.module.cdpt.handler.logic.ProductPlanQueryLogic
import java.util.*

/**
 * @author Romani min
 * @since 2021/2/22 14:19
 */
@Component
open class ValidatePolicyHandler(
    private val productPlanQueryLogic: ProductPlanQueryLogic,
    private val planRateTableQueryLogic: PlanRateTableQueryLogic
) : AbstractTemplateOperate<PolicyIssueReq, PolicyIssueReq>() {
    private val logger = LoggerFactory.getLogger(ValidatePolicyHandler::class.java)

    override fun validation(arg: PolicyIssueReq, result: RestResult<PolicyIssueReq>) {
        if (arg.productPlanId == null) {
            result.mapper(RestResultExt.failureRestResult<PolicyIssueReq>())
            return
        }
        super.validation(arg, result)
    }

    override fun processor(arg: PolicyIssueReq, result: RestResult<PolicyIssueReq>): RestResult<PolicyIssueReq> {
        val productPlanId = arg.productPlanId!!
        val page = productPlanQueryLogic.page(PageQuery.eqQuery("id", productPlanId))
        val productPlan = page.records.firstOrNull()
        if (productPlan == null) {
            logger.error("Can't find product-plan[{}] in system.", productPlanId)
            result.mapper(RestResultExt.failureRestResult<PolicyIssueReq>())
        }
        val waitingDays = productPlan?.waitingDays ?: 0
        if (waitingDays == 0) {
            // 即时起保的产品仍然有【两小时】的起保间隔期
            arg.startTime = DateUtil.offsetHour(arg.startTime, 2).toJdkDate()
        }
        val betweenDay = DateUtil.betweenDay(Date(), arg.startTime, true)
        if (betweenDay < waitingDays) {
            logger.error("Insure start time is invalid, product has {} waiting days.", waitingDays)
            result.mapper(RestResultExt.failureRestResult<PolicyIssueReq>())
        }
        val travelDays = DateUtil.betweenDay(arg.startTime, arg.endTime, true) + 1
        val rateTable = planRateTableQueryLogic.fetchAllOfPlan(productPlanId)
        // TODO 保费计算引入规则引擎
        val hitRateRecord = rateTable.firstOrNull { it?.dayStart!! <= travelDays && it.dayEnd!! >= travelDays }
        if (hitRateRecord == null) {
            logger.error("travel days {} is not hit in rate-table.", travelDays)
            result.mapper(RestResultExt.failureRestResult<PolicyIssueReq>())
        }
        // 单位保费
        val unitPremium = hitRateRecord!!.premium
        // TODO 保费计算
        return result.success(arg)
    }
}