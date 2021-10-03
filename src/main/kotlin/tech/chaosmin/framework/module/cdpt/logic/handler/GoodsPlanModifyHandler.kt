/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.handler

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.service.GoodsPlanService
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.GoodsPlanMapper
import tech.chaosmin.framework.module.cdpt.logic.interrogator.ProductInterrogator
import tech.chaosmin.framework.module.cdpt.logic.interrogator.ProductPlanInterrogator
import tech.chaosmin.framework.module.mgmt.entity.UserEntity
import tech.chaosmin.framework.module.mgmt.logic.interrogator.UserInterrogator
import tech.chaosmin.framework.utils.JsonUtil
import tech.chaosmin.framework.utils.SecurityUtil
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class GoodsPlanModifyHandler(
    private val goodsPlanService: GoodsPlanService,
    private val productInterrogator: ProductInterrogator,
    private val productPlanInterrogator: ProductPlanInterrogator,
    private val userInterrogator: UserInterrogator
) : AbstractTemplateOperate<GoodsPlanEntity, GoodsPlanEntity>() {
    private val logger = LoggerFactory.getLogger(GoodsPlanModifyHandler::class.java)

    override fun validation(arg: GoodsPlanEntity, res: RestResult<GoodsPlanEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    @Transactional
    override fun processor(arg: GoodsPlanEntity, res: RestResult<GoodsPlanEntity>): RestResult<GoodsPlanEntity> {
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
            ModifyTypeEnum.UPDATE -> goodsPlanService.updateById(GoodsPlanMapper.INSTANCE.toDO(arg))
            ModifyTypeEnum.REMOVE -> goodsPlanService.removeById(arg.id)
            else -> throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_FUNCTION.code)
        }
        return res.success(arg)
    }

    private fun createGoodsPlan(arg: GoodsPlanEntity): Map<String, Set<Long>> {
        if (arg.plans == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "plans")
        }
        val failedRecord = mutableMapOf<String, Set<Long>>()
        arg.userIds?.mapNotNull { userInterrogator.getOne(it) }?.forEach {
            val list = createUserGoodsPlan(it, arg.plans!!)
            if (list.isNotEmpty()) failedRecord[it.username!!] = list
        }
        return failedRecord
    }

    private fun createUserGoodsPlan(user: UserEntity, plans: Map<Long, Double>): Set<Long> {
        val authorizer = SecurityUtil.getUserDetails()
        val userId = user.id!!
        val filteredPlans = plans.filter { (planId, _) -> goodsPlanService.getByUserAndPlan(userId, planId) == null }
        filteredPlans.forEach { (planId, comsRatio) ->
            val plan = productPlanInterrogator.getOne(planId)
            plan?.run {
                productInterrogator.getOne(plan.productId!!)?.run {
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