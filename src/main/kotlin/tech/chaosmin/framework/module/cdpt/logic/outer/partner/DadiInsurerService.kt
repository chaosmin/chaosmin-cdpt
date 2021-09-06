/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * DadiInsurerService.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.outer.partner

import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import sun.misc.BASE64Encoder
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.PolicyProcessEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.channel.BaseChannelReq
import tech.chaosmin.framework.module.cdpt.entity.properties.DadiInsurerProperties
import tech.chaosmin.framework.module.cdpt.logic.handler.ThirdPartyRequestHandler
import tech.chaosmin.framework.module.cdpt.logic.outer.PartnerInterfaceService
import tech.chaosmin.framework.utils.JsonUtil
import tech.chaosmin.framework.utils.SignUtil
import java.nio.charset.Charset
import javax.annotation.Resource
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * @author Romani min
 * @since 2021/9/4 13:37
 */
@Service
@EnableConfigurationProperties(value = [DadiInsurerProperties::class])
open class DadiInsurerService(thirdPartyRequestHandler: ThirdPartyRequestHandler) : PartnerInterfaceService(thirdPartyRequestHandler) {
    private val logger = LoggerFactory.getLogger(DadiInsurerService::class.java)

    @Resource
    lateinit var properties: DadiInsurerProperties

    override fun getRequestInfo(process: PolicyProcessEnum): RequestDTO {
        val api = properties.apiList?.firstOrNull { it.process == process }
            ?: throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_PARAM_TYPE, process.name)
        return RequestDTO(api.method!!, properties.server!!, api.url!!)
    }

    override fun header(process: PolicyProcessEnum, body: BaseChannelReq): Map<String, String> {
        val (method, _, `interface`) = getRequestInfo(process)
        logger.info("大地保险明文请求报文: ${JsonUtil.encode(body)}")
        val bodyBytes = JsonUtil.encode(body).toByteArray(Charset.defaultCharset())
        val bodyMD5 = SignUtil.base64AndMD5(bodyBytes)
        logger.info("大地保险密文请求报文: $bodyMD5")
        // 请求方式(大写)\n请求报文MD5\n请求链接(不包含域名)
        val content = "${method}\n$bodyMD5\n${`interface`}"
        logger.info("大地保险明文签名: $content")
        val keyBytes = properties.securityKey?.toByteArray(Charset.defaultCharset())
        val contentBytes = content.toByteArray(Charset.defaultCharset())
        val mac = Mac.getInstance("HmacSHA256")
        val key = SecretKeySpec(keyBytes, "HmacSHA256")
        mac.init(key)
        val digestBytes = mac.doFinal(contentBytes)
        val encoder = BASE64Encoder()
        val builder = StringBuilder()
        builder.append(encoder.encode(digestBytes))
        val signature = builder.toString()
        logger.info("大地保险密文签名: $signature")
        return mapOf("AG-Access-Key" to (properties.accessKey ?: ""), "AG-Signature" to signature)
    }
}