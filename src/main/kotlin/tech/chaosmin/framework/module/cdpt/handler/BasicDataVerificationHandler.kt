package tech.chaosmin.framework.module.cdpt.handler

import cn.hutool.core.date.DateUtil
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.StatusEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.handler.logic.GoodsPlanQueryLogic
import tech.chaosmin.framework.module.cdpt.handler.logic.PolicyQueryLogic
import tech.chaosmin.framework.utils.SecurityUtil
import java.math.BigDecimal
import java.util.*

/**
 * 投保数据有效性及幂等性校验 <p>
 * <p>
 * STEP 1 订单号幂等性校验, 判断该订单号是否已生成相应的保单数据, 如果已生成有效的保单数据则阻断流程 <p>
 * STEP 2 校验投保产品的有效性 <p>
 * STEP 3 保费复算 <p>
 * <p>
 * @author Romani min
 * @since 2021/2/22 14:19
 */
@Component
open class BasicDataVerificationHandler(
    private val policyQueryLogic: PolicyQueryLogic,
    private val goodsPlanQueryLogic: GoodsPlanQueryLogic
) : AbstractTemplateOperate<PolicyIssueReq, PolicyIssueReq>() {
    private val logger = LoggerFactory.getLogger(BasicDataVerificationHandler::class.java)

    override fun validation(arg: PolicyIssueReq, result: RestResult<PolicyIssueReq>) {
        if (arg.orderNo == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "订单号[orderNo]")
        }
        if (arg.goodsPlanId == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "授权产品[goodsPlanId]")
        }
        super.validation(arg, result)
    }

    override fun processor(arg: PolicyIssueReq, result: RestResult<PolicyIssueReq>): RestResult<PolicyIssueReq> {
        // step 1
        orderNoIdempotenceCheck(arg)
        // step 2
        productValidityCheck(arg)
        // step 3
        premiumRecalculation(arg)
        // success
        return result.success(arg)
    }

    private fun orderNoIdempotenceCheck(arg: PolicyIssueReq) {
        val pageRes = policyQueryLogic.page(PageQuery.eqQuery("order_no", arg.orderNo))
        if ((pageRes.records.size > 0)) {
            logger.warn("OrderNo[${arg.orderNo}] has insured policy already.")
            throw FrameworkException(ErrorCodeEnum.RESOURCE_EXISTED.code, "该订单号")
        }
    }

    private fun productValidityCheck(arg: PolicyIssueReq) {
        val goodsPlanId = arg.goodsPlanId!!
        val goodsPlanEntity = goodsPlanQueryLogic.get(goodsPlanId)
        // 产品无效, 返回校验失败
        if (goodsPlanEntity == null || StatusEnum.DISABLED == goodsPlanEntity.status) {
            logger.error("Goods plan[$goodsPlanId] has been invalided.")
            throw FrameworkException(ErrorCodeEnum.RESOURCE_INVALID.code, "产品授权")
        }
        if (goodsPlanEntity.userId != SecurityUtil.getUserId()) {
            logger.error("Goods plan[$goodsPlanId] does not assigned to user[${SecurityUtil.getUserId()}]")
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_INVALID.code, "您无权出单该产品")
        }
        arg.goodsPlan = goodsPlanEntity

        val waitingDays = arg.goodsPlan?.waitingDays ?: 0
        // 即时起保的产品仍然有【两小时】的起保间隔期
        if (waitingDays == 0) arg.startTime = DateUtil.offsetHour(arg.startTime, 2).toJdkDate()
        // 计算保单有效期起期与当前时间的差值是否小于起保等待期(即提前生效)
        val betweenDay = DateUtil.betweenDay(Date(), arg.startTime, true)
        if (betweenDay < waitingDays) {
            logger.error("Insure start time is invalid, product has $waitingDays waiting days.")
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_INVALID.code, "保单起期")
        }
    }

    private fun premiumRecalculation(arg: PolicyIssueReq) {
        val travelDays = DateUtil.betweenDay(arg.startTime, arg.endTime, true) + 1
        if (travelDays != arg.days?.toLong() ?: 0) {
            logger.warn("The number of days calculated on the front[${arg.days}] and back[$travelDays] does not match!")
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_INVALID.code, "旅行天数")
        }
        val hitRateRecord = arg.goodsPlan?.rateTable?.firstOrNull { it.dayStart!! <= travelDays && it.dayEnd!! >= travelDays }
        if (hitRateRecord == null) {
            logger.error("Days[$travelDays] is not hit in the rate table.")
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_INVALID.code, "旅行天数&费率表")
        }
        // backend 单位保费
        val premium = hitRateRecord.premium!!
        if (premium != arg.unitPremium) {
            logger.warn("The unit of premium on the front[${arg.unitPremium}] and back[$premium] does not match!")
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_INVALID.code, "单位保费")
        }
        // 重新计算总保费
        arg.totalPremium = premium * (arg.insuredList?.size ?: 0)
        if (arg.totalPremium == null || arg.totalPremium == 0.0) {
            logger.error("Total premium of this order is zero.")
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_INVALID.code, "总保费")
        }
        val comsRatio = arg.goodsPlan?.comsRatio ?: 0.0
        arg.actualPremium = (BigDecimal(100).minus(BigDecimal(comsRatio)))
            .divide(BigDecimal(100)).multiply(BigDecimal(arg.totalPremium!!)).toDouble()
        if (arg.actualPremium == null || arg.actualPremium == 0.0) {
            logger.error("Actual premium of this order is zero.")
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_INVALID.code, "实收保费")
        }
        logger.info("Order[${arg.orderNo}] => premium: ${arg.unitPremium}, total-premium: ${arg.totalPremium}, actual-premium: ${arg.actualPremium}")
    }
}