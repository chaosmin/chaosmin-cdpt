package tech.chaosmin.framework.module.cdpt.logic.message

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import tech.chaosmin.framework.utils.JsonUtil


/**
 * @author Romani min
 * @since 2021/9/3 16:07
 */
@Component
class QueueMessageListener {
    private val logger = LoggerFactory.getLogger(QueueMessageListener::class.java)

    @RabbitListener(queues = ["policy"])
    fun onRegistrationMessageFromMailQueue(message: Any?) {
        logger.info("Queue [policy] received message: {}", JsonUtil.encode(message))
    }
}