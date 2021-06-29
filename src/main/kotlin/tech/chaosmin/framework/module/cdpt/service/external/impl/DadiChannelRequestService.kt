package tech.chaosmin.framework.module.cdpt.service.external.impl

import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import sun.misc.BASE64Encoder
import tech.chaosmin.framework.base.enums.PolicyProcessEnum
import tech.chaosmin.framework.configuration.channel.DadiInsurerProperties
import tech.chaosmin.framework.module.cdpt.entity.channel.BaseChannelReq
import tech.chaosmin.framework.module.cdpt.service.external.ChannelRequestService
import tech.chaosmin.framework.module.mgmt.service.ChannelHttpRequestService
import tech.chaosmin.framework.utils.JsonUtil
import tech.chaosmin.framework.utils.SignUtil
import java.nio.charset.Charset
import javax.annotation.Resource
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * @author Romani min
 * @since 2021/6/21 15:42
 */
@Service
@EnableConfigurationProperties(value = [DadiInsurerProperties::class])
class DadiChannelRequestService(private val channelHttpRequestService: ChannelHttpRequestService) :
    ChannelRequestService(channelHttpRequestService) {
    private val logger = LoggerFactory.getLogger(DadiChannelRequestService::class.java)

    @Resource
    lateinit var properties: DadiInsurerProperties

    override val server: String by lazy { properties.server }

    override val requestUrl: Map<PolicyProcessEnum, String> by lazy {
        mutableMapOf(
            PolicyProcessEnum.PREMIUM_TRIAL to properties.calculatePremiumUrl,
            PolicyProcessEnum.UNDERWRITING to properties.underwritingUrl,
            PolicyProcessEnum.POLICY_CANCEL to properties.cancelPolicyUrl
        )
    }

    override fun header(url: String, body: BaseChannelReq): Map<String, String> {
        val bodyBytes = JsonUtil.encode(body).toByteArray(Charset.defaultCharset())
        val bodyMD5 = SignUtil.base64AndMD5(bodyBytes)
        val content = "POST\n$bodyMD5\n$url"
        logger.info("Dadi content => $content")
        val keyBytes = properties.securityKey.toByteArray(Charset.defaultCharset())
        val contentBytes = content.toByteArray(Charset.defaultCharset())
        val mac = Mac.getInstance("HmacSHA256")
        val key = SecretKeySpec(keyBytes, "HmacSHA256")
        mac.init(key)
        val digestBytes = mac.doFinal(contentBytes)
        val encoder = BASE64Encoder()
        val builder = StringBuilder()
        builder.append(encoder.encode(digestBytes))
        val signature = builder.toString()
        return mapOf(
            "AG-Access-Key" to properties.accessKey,
            "AG-Signature" to signature
        )
    }
}