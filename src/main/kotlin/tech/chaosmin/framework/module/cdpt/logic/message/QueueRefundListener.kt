/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.message

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.utils.JsonUtil

/**
 * @author Romani min
 * @since 2021/9/5 14:41
 */
@Component
class QueueRefundListener {
    private val logger = LoggerFactory.getLogger(QueueRefundListener::class.java)

    @RabbitListener(queues = ["refund"])
    fun consumer(message: PolicyEntity?) {
        if (message == null) return
        logger.info("Queue [policy] received message: {}", JsonUtil.encode(message))
    }
}