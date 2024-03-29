/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.service.PolicyHolderService
import tech.chaosmin.framework.module.cdpt.domain.service.PolicyInsurantService
import tech.chaosmin.framework.module.cdpt.domain.service.PolicyService
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.entity.enums.PayStatusEnum
import tech.chaosmin.framework.module.cdpt.entity.enums.PayTypeEnum
import tech.chaosmin.framework.module.cdpt.entity.enums.PolicyStatusEnum
import tech.chaosmin.framework.module.cdpt.logic.convert.PolicyHolderMapper
import tech.chaosmin.framework.module.cdpt.logic.convert.PolicyInsurantMapper
import tech.chaosmin.framework.module.cdpt.logic.convert.PolicyMapper
import tech.chaosmin.framework.module.payment.entity.wechat.request.NativeRefundReq
import tech.chaosmin.framework.module.payment.logic.service.WechatNativePayService
import tech.chaosmin.framework.utils.JsonUtil
import java.math.BigDecimal
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/26 15:57
 */
@Component
open class PolicyModifyHandler(
    private val policyService: PolicyService,
    private val policyHolderService: PolicyHolderService,
    private val policyInsurantService: PolicyInsurantService,
    private val wechatNativePayService: WechatNativePayService
) : AbstractTemplateOperate<PolicyEntity, PolicyEntity>() {
    private val logger = LoggerFactory.getLogger(PolicyModifyHandler::class.java)

    override fun validation(arg: PolicyEntity, res: RestResult<PolicyEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    @Transactional
    override fun processor(arg: PolicyEntity, res: RestResult<PolicyEntity>): RestResult<PolicyEntity> {
        val policy = PolicyMapper.INSTANCE.toDO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> {
                logger.info("policy.create.input: $policy")
                policyService.save(policy)

                val policyHolder = PolicyHolderMapper.INSTANCE.toDO(arg.holder)
                policyHolder?.policyId = policy.id
                logger.info("policy-holder.create.input: $policyHolder")
                policyHolderService.save(policyHolder)

                val insuredList = PolicyInsurantMapper.INSTANCE.toDO(arg.insuredList)?.map { it.apply { policyId = policy.id } }
                logger.info("policy-insurant.create.input: $insuredList")
                policyInsurantService.saveBatch(insuredList)
            }
            ModifyTypeEnum.UPDATE -> {
                logger.info("policy.update.input: $policy")
                val exist = policyService.getById(arg.id)
                logger.info("policy.update.exist: $exist")

                when (arg.status) {
                    // 承保参数校验
                    PolicyStatusEnum.UNDERWRITING_PASS -> {
                    }
                    PolicyStatusEnum.INSURED -> {
                        policy.issueTime = Date()
                        if (policy.ePolicyUrl.isNullOrBlank()) {
                            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL, "电子保单下载链接[ePolicyUrl]")
                        }
                    }
                    // 退保参数校验
                    PolicyStatusEnum.SURRENDERED -> {
                        policy.cancelTime = Date()
                        // 支付状态为成功, 则需要更新支付状态
                        if (exist.payStatus == PayStatusEnum.PAYMENT_SUCCESSFUL.getCode()) {
                            // 如果保单支付渠道为线上, 则进行退费动作, 支付回调后更新保单信息
                            if (exist.payType == PayTypeEnum.ONLINE.getCode()) {
                                val refundReq = NativeRefundReq().apply {
                                    this.out_trade_no = exist.orderNo
                                    this.out_refund_no = exist.orderNo
                                    this.reason = "用户主动退保"
                                    // 保费转化为分
                                    val premium = BigDecimal(exist.actualPremium!!).multiply(BigDecimal(100))
                                    this.amount.total = premium.toInt()
                                    this.amount.refund = premium.toInt()
                                }
                                logger.info("保单退保触发退费处理: ${JsonUtil.encode(refundReq)}")
                                wechatNativePayService.refund(refundReq)
                            } else {
                                // 如果保单支付渠道为线下, 直接更新支付状态为已退费
                                policy.payStatus = PayStatusEnum.REFUNDED.getCode()
                                policy.refundTime = Date()
                            }
                        } else {
                            // 如果是未支付的保单, 则更新状态为取消
                            policy.status = PolicyStatusEnum.CANCELLED.getCode()
                        }
                    }
                    else -> throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_FUNCTION, arg.status!!.name)
                }

                // 参数校验通过, 更新保单信息
                policyService.updateById(policy)
            }
            ModifyTypeEnum.REMOVE -> {
                logger.warn("policy.remove.input: $policy")
                policyService.remove(Wrappers.query(policy))
            }
        }
        arg.id = policy.id
        return res.success(arg)
    }
}