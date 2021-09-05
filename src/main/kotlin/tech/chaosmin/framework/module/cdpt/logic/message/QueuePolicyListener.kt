/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * QueuePolicyListener.kt
 *
 * This class is used to monitor the policy information in the message queue 'policy', and perform corresponding new or update operations
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.message

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.logic.handler.PolicyModifyHandler
import tech.chaosmin.framework.module.cdpt.logic.interrogator.PolicyInterrogator
import tech.chaosmin.framework.utils.JsonUtil

/**
 * @author Romani min
 * @since 2021/9/3 16:07
 */
@Component
class QueuePolicyListener(
    private val policyInterrogator: PolicyInterrogator,
    private val policyModifyHandler: PolicyModifyHandler
) {
    private val logger = LoggerFactory.getLogger(QueuePolicyListener::class.java)

    @RabbitListener(queues = ["policy"])
    fun consumer(message: PolicyEntity?) {
        if (message == null) return
        logger.info("Queue [policy] received message: {}", JsonUtil.encode(message))
        when (message.modifyType) {
            ModifyTypeEnum.SAVE -> policyModifyHandler.operate(message)
            ModifyTypeEnum.UPDATE -> {
                // 判断是否含有id
                if (message.id == null) {
                    val policy = policyInterrogator.getOne(Wrappers.query<Policy>().eq("order_no", message.orderNo))
                    if (policy == null) {
                        logger.error("Unable to update policy information because there is neither id[${message.id}] nor order_no[${message.orderNo}].")
                    } else policyModifyHandler.operate(message.update(policy.id))
                } else policyModifyHandler.operate(message)
            }
            else -> logger.warn("unsupported modify type [${message.modifyType}] error.")
        }
    }
}