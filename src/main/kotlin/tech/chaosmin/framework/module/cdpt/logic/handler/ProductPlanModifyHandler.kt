/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.handler

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductPlan
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductPlanLib
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductPlanRaTe
import tech.chaosmin.framework.module.cdpt.domain.service.ProductPlanLibService
import tech.chaosmin.framework.module.cdpt.domain.service.ProductPlanRaTeService
import tech.chaosmin.framework.module.cdpt.domain.service.ProductPlanService
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanEntity
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanLibEntity
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanRaTeEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.ProductPlanLibMapper
import tech.chaosmin.framework.module.cdpt.logic.convert.ProductPlanMapper
import tech.chaosmin.framework.module.cdpt.logic.convert.ProductPlanRaTeMapper

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ProductPlanModifyHandler(
    private val productPlanService: ProductPlanService,
    private val productPlanLibService: ProductPlanLibService,
    private val productPlanRaTeService: ProductPlanRaTeService
) : AbstractTemplateOperate<ProductPlanEntity, ProductPlanEntity>() {
    private val logger = LoggerFactory.getLogger(ProductPlanModifyHandler::class.java)
    private val pid = "product_plan_id"

    override fun validation(arg: ProductPlanEntity, res: RestResult<ProductPlanEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    @Transactional
    override fun processor(arg: ProductPlanEntity, res: RestResult<ProductPlanEntity>): RestResult<ProductPlanEntity> {
        val productPlan = ProductPlanMapper.INSTANCE.toDO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> {
                val productId = productPlan.productId!!
                val planCode = productPlan.planCode
                val exPlans = productPlanService.listEqProductId(productId)
                if (exPlans.isEmpty() || exPlans.none { it.planCode == planCode }) {
                    createPlan(productPlan, arg.liabilities, arg.rateTable)
                } else {
                    logger.warn("ProductPlan[{}][{}] has already be created.", productId, planCode)
                }
            }
            ModifyTypeEnum.UPDATE -> {
                productPlanService.updateById(productPlan)
                val liabilities = arg.liabilities.map { ProductPlanLibMapper.INSTANCE.toDO(it) }
                productPlanLibService.updateBatchById(liabilities)
                val rateTable = arg.rateTable.map { ProductPlanRaTeMapper.INSTANCE.toDO(it) }
                productPlanRaTeService.updateBatchById(rateTable)
            }
            ModifyTypeEnum.REMOVE -> {
                productPlanService.remove(Wrappers.query(productPlan))
                productPlanLibService.remove(QueryWrapper<ProductPlanLib>().eq(pid, productPlan.id))
                productPlanRaTeService.remove(QueryWrapper<ProductPlanRaTe>().eq(pid, productPlan.id))
            }
        }
        return res.success(ProductPlanMapper.INSTANCE.toEn(productPlan))
    }

    private fun createPlan(productPlan: ProductPlan, liabilities: List<ProductPlanLibEntity>, rateTable: List<ProductPlanRaTeEntity>) {
        if (liabilities.isNotEmpty() && rateTable.isNotEmpty()) {
            // 创建时填充计划的主险保额
            productPlan.primaryCoverage = liabilities.first().amount
            productPlanService.save(productPlan)

            productPlanLibService.saveBatch(liabilities.mapIndexedNotNull { index, it ->
                ProductPlanLibMapper.INSTANCE.toDO(it)?.apply {
                    this.productPlanId = productPlan.id
                    this.productPlanCode = productPlan.planCode
                    this.sort = index + 1
                }
            })
            productPlanRaTeService.saveBatch(rateTable.mapIndexedNotNull { index, it ->
                ProductPlanRaTeMapper.INSTANCE.toDO(it)?.apply {
                    this.productPlanId = productPlan.id
                    this.productPlanCode = productPlan.planCode
                    this.sort = index + 1
                }
            })
        } else logger.warn("Plan[{}] is empty, skip create progress.", productPlan.planCode)
    }
}