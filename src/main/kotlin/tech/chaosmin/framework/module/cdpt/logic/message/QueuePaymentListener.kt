/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * QueuePaymentListener.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.message

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.entity.enums.PayStatusEnum
import tech.chaosmin.framework.module.cdpt.logic.interrogator.PolicyInterrogator
import tech.chaosmin.framework.module.payment.entity.PaymentNotifyEntity
import tech.chaosmin.framework.utils.JsonUtil

/**
 * @author Romani min
 * @since 2021/9/5 21:16
 */
@Component
class QueuePaymentListener(
    private val policyInterrogator: PolicyInterrogator,
    private val rabbitTemplate: RabbitTemplate
) {
    private val logger = LoggerFactory.getLogger(QueuePaymentListener::class.java)

    @RabbitListener(queues = ["payment"])
    fun consumer(message: PaymentNotifyEntity?) {
        if (message == null) return
        logger.info("Queue [payment] received message: {}", JsonUtil.encode(message))
        val orderNo = message.orderNo
        val policyEn = policyInterrogator.getOne(Wrappers.query<Policy>().eq("order_no", orderNo))
        if (policyEn == null) {
            logger.error("无法查询到指定订单/保单[$orderNo]")
            return
        }
        when (message.status) {
            // 支付成功的情况
            PayStatusEnum.PAYMENT_SUCCESSFUL -> {
                // 推送保司承保
                logger.info("Send message to queue [insurer], message: ${JsonUtil.encode(policyEn)}")
                rabbitTemplate.convertAndSend("insurer", policyEn)
            }
            // 支付失败的情况
            PayStatusEnum.PAYMENT_FAILED -> {
                policyEn.payStatus = PayStatusEnum.PAYMENT_FAILED
                logger.info("Send message to queue [policy], message: ${JsonUtil.encode(policyEn)}")
                rabbitTemplate.convertAndSend("policy", policyEn.update())
            }
            // 退费成功的情况
            PayStatusEnum.REFUNDED -> {
                policyEn.payStatus = PayStatusEnum.REFUNDED
                logger.info("Send message to queue [policy], message: ${JsonUtil.encode(policyEn)}")
                rabbitTemplate.convertAndSend("policy", policyEn.update())
            }
            else -> logger.error("Unsupported payment status: ${message.status}")
        }

    }
}