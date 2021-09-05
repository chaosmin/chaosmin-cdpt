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

import cn.hutool.core.date.DateUtil
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.enums.PolicyProcessEnum
import tech.chaosmin.framework.module.cdpt.api.convert.ChannelRequestConvert
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.DDResp
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj.DDUResp
import tech.chaosmin.framework.module.cdpt.entity.enums.PayStatusEnum
import tech.chaosmin.framework.module.cdpt.entity.enums.PolicyStatusEnum
import tech.chaosmin.framework.module.cdpt.logic.outer.partner.DadiInsurerService
import tech.chaosmin.framework.utils.JsonUtil

/**
 * @author Romani min
 * @since 2021/9/3 16:07
 */
@Component
class QueueInsurerListener(
    private val dadiInsurerService: DadiInsurerService,
    private val rabbitTemplate: RabbitTemplate
) {
    private val logger = LoggerFactory.getLogger(QueueInsurerListener::class.java)

    @Value("\${test.mock:false}")
    private val mock: Boolean = false

    @RabbitListener(queues = ["insurer"])
    fun consumer(message: PolicyEntity?) {
        if (message == null) return
        logger.info("Queue [insurer] received message: {}", JsonUtil.encode(message))

        val response = dadiInsurerService.request(PolicyProcessEnum.ACCEPTANCE, ChannelRequestConvert.convert2DDUReq(message)) {
            val str = if (mock) {
                """{"responseHead":{"responseTime":"2021-07-09T13:22:53","resultCode":"0","resultMessage":"提交核保接口任务执行成功！","responseId":"6ffd23ec-27bb-4ace-86de-ac6473108aed"},"responseBody":{"isDownloadPolicy":"true","policyNo":"PEGC21310116020000000050","ePolicyURL":"https://iopen-uat.ccic-net.com.cn/management-service/rest/v1/epolicy/download/PEGC21310116020000000050-90D9E3611EFCCB4B6E7D948330FF8970BB737CB93AC280472199F7BE6B373289"}}"""
            } else it
            JsonUtil.decode(str, DDResp::class.java, DDUResp::class.java)
        }
        if ("1" == response?.responseHead?.resultCode) {
            logger.error(response.responseHead?.resultMessage ?: "请求第三方核保接口异常")
        } else {
            // 保司承保成功, 更新保单状态
            message.issueTime = DateUtil.parseUTC(response?.responseHead?.responseTime)
            message.status = PolicyStatusEnum.INSURED
            message.payStatus = PayStatusEnum.PAYMENT_SUCCESSFUL
            message.policyNo = (response?.responseBody as DDUResp).policyNo
            message.ePolicyUrl = (response.responseBody as DDUResp).ePolicyURL
            logger.info("Send message to queue [policy], message: ${JsonUtil.encode(message)}")
            rabbitTemplate.convertAndSend("policy", message.update())
        }
    }
}