package tech.chaosmin.framework.module.cdpt.handler

import cn.hutool.core.date.DateUtil
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.base.enums.YesNoEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.dataobject.GoodsPlan
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.handler.logic.ProductPlanQueryLogic
import tech.chaosmin.framework.module.cdpt.handler.logic.ProductQueryLogic
import tech.chaosmin.framework.module.cdpt.helper.mapper.GoodsPlanMapper
import tech.chaosmin.framework.module.cdpt.service.GoodsPlanService
import tech.chaosmin.framework.module.mgmt.entity.UserEntity
import tech.chaosmin.framework.module.mgmt.handler.logic.UserQueryLogic
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
            ModifyTypeEnum.SAVE -> createGoodsPlan(arg)
            ModifyTypeEnum.UPDATE -> updateGoodsPlan(arg)
            ModifyTypeEnum.REMOVE -> goodsPlanService.removeById(arg.id)
            else -> throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_FUNCTION.code)
        }
        return result.success(arg)
    }

    private fun createGoodsPlan(arg: GoodsPlanEntity) {
        if (arg.plans == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "plans")
        }
        arg.userIds?.forEach { userId ->
            userQueryLogic.get(userId)?.run {
                createUserGoodsPlan(this, arg.plans!!)
            }
        }
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

    private fun createUserGoodsPlan(user: UserEntity, plans: Map<Long, Double>) {
        val authorizeTime = Date()
        val userDetails = SecurityUtil.getUserDetails()
        val isOfficer = user.role?.contains("officer") ?: false
        plans.forEach { (planId, comsRatio) ->
            val exPlan = goodsPlanService.getByUserAndPlan(user.id!!, planId)
            if (exPlan != null) {
                logger.error("product-plan[{}] has already auth to user[{}]", exPlan.id, user.id)
                // goodsPlanService.removeById(exPlan.id)
                throw FrameworkException(ErrorCodeEnum.FAILURE.code, "已为用户[${user.username}]分配过计划[${exPlan.productPlanId}]")
            }
            val plan = productPlanQueryLogic.get(planId)
            if (plan != null) {
                val product = productQueryLogic.get(plan.productId!!)
                goodsPlanService.save(GoodsPlan().apply {
                    this.productId = product?.id
                    this.productPlanId = planId
                    this.departmentName = user.department
                    this.userId = user.id
                    this.userName = user.username
                    this.partnerCode = product?.partnerCode
                    this.partnerName = product?.partnerName
                    this.isForSale = if (isOfficer) YesNoEnum.YES.getCode() else YesNoEnum.NO.getCode()
                    this.saleStartTime = DateUtil.beginOfDay(authorizeTime)
                    this.saleEndTime = DateUtil.offsetMonth(this.saleStartTime, 1200)
                    this.authorizeTime = authorizeTime
                    this.authorizerId = userDetails?.userId
                    this.authorizer = userDetails?.userName
                    this.comsRatio = comsRatio
                })
            }
        }
    }
}