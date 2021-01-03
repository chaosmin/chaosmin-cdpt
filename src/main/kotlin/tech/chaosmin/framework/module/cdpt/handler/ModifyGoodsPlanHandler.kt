package tech.chaosmin.framework.module.cdpt.handler

import cn.hutool.core.date.DateUtil
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
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
            ModifyTypeEnum.UPDATE -> goodsPlanService.updateById(GoodsPlanMapper.INSTANCE.convert2DO(arg))
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

    private fun createUserGoodsPlan(user: UserEntity, plans: Map<Long, Double>) {
        val authorizeTime = Date()
        val isOfficer = user.role.equals("officer", true)
        plans.forEach { (planId, comsRatio) ->
            val exPlan = goodsPlanService.getEqUserAndPlan(user.id!!, planId)
            if (exPlan != null) {
                logger.warn("overwrite user goods-plan[{}]", exPlan.id)
                goodsPlanService.removeById(exPlan.id)
            }
            val plan = productPlanQueryLogic.get(planId)
            if (plan != null) {
                val product = productQueryLogic.get(plan.productId!!)
                goodsPlanService.save(GoodsPlan().apply {
                    this.productId = product?.id
                    this.productPlanId = planId
                    this.departmentName = user.department
                    this.roleName = user.role
                    this.userId = user.id
                    this.userName = user.username
                    this.partnerCode = product?.partnerCode
                    this.partnerName = product?.partnerName
                    // TODO 管理员授权的账户没有出单权限?
                    this.isForSale = isOfficer
                    this.saleStartTime = authorizeTime
                    this.saleEndTime = DateUtil.offsetDay(authorizeTime, 365)
                    this.authorizeTime = authorizeTime
                    this.authorizer = SecurityUtil.getUsername()
                    this.comsRatio = comsRatio
                })
            }
        }
    }
}