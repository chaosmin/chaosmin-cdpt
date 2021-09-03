package tech.chaosmin.framework.module.cdpt.logic.message

import org.junit.jupiter.api.Test
import org.springframework.amqp.rabbit.core.RabbitTemplate
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/9/3 16:14
 */
class QueueMessageListenerBootTest : BaseTestMain() {
    @Resource
    lateinit var rabbitTemplate: RabbitTemplate

    @Test
    fun test() {
        repeat((0..10).count()) {
            rabbitTemplate.convertAndSend("policy", Policy())
            println("send $it")
            Thread.sleep(1000)
        }
    }
}