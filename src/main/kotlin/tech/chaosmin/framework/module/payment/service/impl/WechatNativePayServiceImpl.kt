package tech.chaosmin.framework.module.payment.service.impl

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.utils.URIBuilder
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.util.EntityUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.definition.WechatPayParam
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.payment.entity.wechat.request.NativeReq
import tech.chaosmin.framework.module.payment.entity.wechat.response.NativeResp
import tech.chaosmin.framework.module.payment.service.WechatNativePayService
import tech.chaosmin.framework.utils.JsonUtil
import java.io.ByteArrayInputStream
import java.nio.charset.Charset
import java.util.*

/**
 * 微信支付 外部服务
 *
 * @author Romani min
 * @since 2021/8/23 16:39
 */
@Service
open class WechatNativePayServiceImpl(private val wechatPayParam: WechatPayParam) : WechatNativePayService {
    private val logger = LoggerFactory.getLogger(WechatNativePayService::class.java)
    private var httpClient: CloseableHttpClient? = null

    @Synchronized
    private fun getClient(): CloseableHttpClient {
        if (httpClient == null) {
            val byteArray = wechatPayParam.privateKey?.toByteArray(Charset.forName("utf-8"))
            val privateKey = PemUtil.loadPrivateKey(ByteArrayInputStream(byteArray))
            val verifier = AutoUpdateCertificatesVerifier(
                WechatPay2Credentials(wechatPayParam.merchantId, PrivateKeySigner(wechatPayParam.serialNumber, privateKey)),
                wechatPayParam.v3Key?.toByteArray(Charset.forName("utf-8"))
            )
            val builder = WechatPayHttpClientBuilder.create()
                .withMerchant(wechatPayParam.merchantId, wechatPayParam.serialNumber, privateKey)
                .withValidator(WechatPay2Validator(verifier))
            httpClient = builder.build()
            if (httpClient == null) throw FrameworkException(ErrorCodeEnum.SYSTEM_ERROR.code, "初始化微信支付失败, 请联系管理员")
        }
        return httpClient!!
    }

    private fun beforeRequest(req: NativeReq): NativeReq {
        req.appid = wechatPayParam.appId
        req.mchid = wechatPayParam.merchantId
        req.out_trade_no = UUID.randomUUID().toString().replace("-", "").take(32)
        req.notify_url = "https://weixin.qq.com/"
        return req
    }

    override fun token() {
        val uriBuilder = URIBuilder(wechatPayParam.url?.get(wechatPayParam.CERTIFICATES))
        val httpGet = HttpGet(uriBuilder.build())
        httpGet.addHeader("Accept", "application/json")
        getClient().execute(httpGet).use {
            val bodyAsString: String = EntityUtils.toString(it.entity)
            println(bodyAsString)
        }
    }

    override fun createOrder(req: NativeReq): NativeResp {
        val httpPost = HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/native")
        httpPost.addHeader("Accept", "application/json")
        httpPost.addHeader("Content-type", "application/json; charset=utf-8")
        val requestStr = JsonUtil.encode(beforeRequest(req))
        logger.info("wechat.native.create_order param: $requestStr")
        httpPost.entity = StringEntity(requestStr, "UTF-8")
        getClient().execute(httpPost).use {
            val bodyAsString = EntityUtils.toString(it.entity)
            println(bodyAsString)
        }
        return NativeResp()
    }

    override fun searchOrder() {
        TODO("Not yet implemented")
    }

    override fun closeOrder() {
        TODO("Not yet implemented")
    }

    override fun notifyPay() {
        TODO("Not yet implemented")
    }

    override fun refund() {
        TODO("Not yet implemented")
    }

    override fun notifyRefund() {
        TODO("Not yet implemented")
    }
}