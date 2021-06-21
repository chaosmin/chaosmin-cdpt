package tech.chaosmin.framework.module.cdpt.service.external.impl

import cn.hutool.http.HttpRequest
import cn.hutool.http.HttpUtil
import cn.hutool.http.Method
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import sun.misc.BASE64Encoder
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.DDReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDCPReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDCReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDUReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.DDResp
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj.DDCPResp
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj.DDCResp
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj.DDUResp
import tech.chaosmin.framework.module.cdpt.service.external.DadiPolicyService
import tech.chaosmin.framework.utils.JsonUtil
import tech.chaosmin.framework.utils.SignUtil
import java.nio.charset.Charset
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


/**
 * 大地保险 保单处理类 <p>
 *
 * @author Romani min
 * @since 2021/6/16 13:58
 */
@Service
class DadiPolicyServiceImpl : DadiPolicyService {
    private val logger = LoggerFactory.getLogger(DadiPolicyService::class.java)

    private val server = "https://iopen-uat.ccic-net.com.cn"
    private val accessKey = "maMMK7ZrZlyKc8WK"
    private val securityKey = "Evlbxk7ZMfZv0bDa4cXo7P44OjUw5l7Q"

    private fun doBefore(httpRequest: HttpRequest, signature: String): HttpRequest {
        httpRequest.header("AG-Access-Key", accessKey)
        httpRequest.header("AG-Signature", signature)
        return httpRequest
    }

    private fun signature(url: String, body: String): String {
        val bodyBytes = body.toByteArray(Charset.defaultCharset())
        val bodyMD5 = SignUtil.base64AndMD5(bodyBytes)
        val content = "POST\n$bodyMD5\n$url"
        logger.info("Dadi content => $content")
        val keyBytes = securityKey.toByteArray(Charset.defaultCharset())
        val contentBytes = content.toByteArray(Charset.defaultCharset())
        val mac = Mac.getInstance("HmacSHA256")
        val key = SecretKeySpec(keyBytes, "HmacSHA256")
        mac.init(key)
        val digestBytes = mac.doFinal(contentBytes)
        val encoder = BASE64Encoder()
        val builder = StringBuilder()
        builder.append(encoder.encode(digestBytes))
        val signature = builder.toString()
        logger.info("Dadi signature => $signature")
        return signature
    }

    override fun calculatePremium(request: DDReq<*>): DDResp<*> {
        if (request.requestBody is DDCReq) {
            val url = "/CCIC/1.0/ah/commonProposalQuotePrice"
            val body = JsonUtil.encode(request)
            logger.info("request string: $body")
            val httpRequest = doBefore(HttpUtil.createRequest(Method.POST, "${server}${url}"), signature(url, body))
            httpRequest.timeout(60 * 1000).body(body).execute().use {
                val responseBody = it.body()
                val resp = JsonUtil.decode(responseBody, DDResp::class.java, DDCResp::class.java)
                return if (resp == null) {
                    logger.error("Failed to parse response string: $responseBody")
                    DDResp<DDCResp>()
                } else resp
            }
        } else throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_PARAM_TYPE.code)
    }

    override fun underwriting(request: DDReq<*>): DDResp<*> {
        if (request.requestBody is DDUReq) {
            val url = "/CCIC/1.0/ah/commonSubmitUnderWritingPayment"
            val body = JsonUtil.encode(request)
            val httpRequest = doBefore(HttpUtil.createRequest(Method.POST, "${server}${url}"), signature(url, body))
            httpRequest.timeout(60 * 1000).body(body).execute().use {
                val responseBody = it.body()
                val resp = JsonUtil.decode(responseBody, DDResp::class.java, DDUResp::class.java)
                return if (resp == null) {
                    logger.error("Failed to parse response string: $responseBody")
                    DDResp<DDUResp>()
                } else resp
            }
        } else throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_PARAM_TYPE.code)
    }

    override fun cancelPolicy(request: DDReq<*>): DDResp<*> {
        if (request.requestBody is DDCPReq) {
            val url = "/CCIC/1.0/ah/commonCancelPolicy"
            val body = JsonUtil.encode(request)
            val httpRequest = doBefore(HttpUtil.createRequest(Method.POST, "${server}${url}"), signature(url, body))
            httpRequest.timeout(60 * 1000).body(body).execute().use {
                val responseBody = it.body()
                val resp = JsonUtil.decode(responseBody, DDResp::class.java, DDCPResp::class.java)
                return if (resp == null) {
                    logger.error("Failed to parse response string: $responseBody")
                    DDResp<DDCPResp>()
                } else resp
            }
        } else throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_PARAM_TYPE.code)
    }

    override fun refundPolicy(request: DDReq<*>): DDResp<*> {
        val url = "/CCIC/1.0/ah/commonCancelPolicy"
        throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_FUNCTION.code)
    }
}