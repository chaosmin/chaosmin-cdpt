package tech.chaosmin.framework.module.cdpt.handler

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.handler.logic.ProductPlanQueryLogic
import tech.chaosmin.framework.module.cdpt.handler.logic.ProductQueryLogic
import tech.chaosmin.framework.module.cdpt.helper.mapper.GoodsPlanMapper
import tech.chaosmin.framework.module.cdpt.service.inner.GoodsPlanService
import tech.chaosmin.framework.module.mgmt.entity.UserEntity
import tech.chaosmin.framework.module.mgmt.handler.logic.UserQueryLogic
import tech.chaosmin.framework.utils.JsonUtil
import tech.chaosmin.framework.utils.SecurityUtil
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyGoodsPlanHandler(
    private val goodsPlanService: GoodsPlanService,
    private val productQueryLogic: ProductQueryLogic,
    private val productPlanQueryLogic: ProductPlanQueryLogic,
    private val userQueryLogic: UserQueryLogic
) : AbstractTemplateOperate<GoodsPlanEntity, GoodsPlanEntity>() {
    private val logger = LoggerFactory.getLogger(ModifyGoodsPlanHandler::class.java)

    override fun validation(arg: GoodsPlanEntity, result: RestResult<GoodsPlanEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    @Transactional
    override fun processor(arg: GoodsPlanEntity, result: RestResult<GoodsPlanEntity>): RestResult<GoodsPlanEntity> {
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> {
                val failedRecord = createGoodsPlan(arg)
                if (failedRecord.isNotEmpty()) {
                    val joiner = StringJoiner("\n")
                    failedRecord.forEach { (username, list) ->
                        joiner.add("User[$username] has already assigned these product plan: ${JsonUtil.encode(list)}")
                    }
                    logger.warn(joiner.toString())
                    return RestResultExt.successRestResult(msg = joiner.toString(), data = arg)
                }
            }
            ModifyTypeEnum.UPDATE -> updateGoodsPlan(arg)
            ModifyTypeEnum.REMOVE -> goodsPlanService.removeById(arg.id)
            else -> throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_FUNCTION.code)
        }
        return result.success(arg)
    }

    private fun createGoodsPlan(arg: GoodsPlanEntity): Map<String, Set<Long>> {
        if (arg.plans == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "plans")
        }
        val failedRecord = mutableMapOf<String, Set<Long>>()
        arg.userIds?.mapNotNull { userQueryLogic.get(it) }?.forEach {
            val list = createUserGoodsPlan(it, arg.plans!!)
            if (list.isNotEmpty()) failedRecord[it.username!!] = list
        }
        return failedRecord
    }

    private fun updateGoodsPlan(arg: GoodsPlanEntity) {
        val goodsPlan = GoodsPlanMapper.INSTANCE.convert2DO(arg)
        // 更新授权时间范围
        if (!arg.saleDateScope.isNullOrEmpty() && arg.saleDateScope!!.size == 2) {
            goodsPlan?.saleStartTime = arg.saleDateScope!![0]
            goodsPlan?.saleEndTime = arg.saleDateScope!![1]
        }
        goodsPlanService.updateById(goodsPlan)
    }

    private fun createUserGoodsPlan(user: UserEntity, plans: Map<Long, Double>): Set<Long> {
        val authorizer = SecurityUtil.getUserDetails()
        val userId = user.id!!
        val filteredPlans = plans.filter { (planId, _) -> goodsPlanService.getByUserAndPlan(userId, planId) == null }
        filteredPlans.forEach { (planId, comsRatio) ->
            val plan = productPlanQueryLogic.get(planId)
            plan?.run {
                productQueryLogic.get(plan.productId!!)?.run {
                    val goodsPlan = GoodsPlanMapper.INSTANCE.buildDO(user, this, plan).apply {
                        this.authorizerId = authorizer.userId
                        this.authorizer = authorizer.userName
                        // 设置授权最大佣金比例
                        val authorizerPlan = goodsPlanService.getByUserAndPlan(authorizer.userId!!, planId)
                        this.maxComsRatio = if (authorizerPlan == null) plan.comsRatio else authorizerPlan.comsRatio
                        this.comsRatio = comsRatio
                    }
                    goodsPlanService.save(goodsPlan)
                }
            }
        }
        return plans.keys - filteredPlans.keys
    }
}