package tech.chaosmin.framework.module.payment.service.impl

import cn.hutool.core.img.ImgUtil
import cn.hutool.extra.qrcode.QrCodeUtil
import cn.hutool.extra.qrcode.QrConfig
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
import tech.chaosmin.framework.base.enums.HttpMethodEnum
import tech.chaosmin.framework.base.enums.TradeChannelEnum
import tech.chaosmin.framework.base.enums.TransactionStatusEnum
import tech.chaosmin.framework.definition.WechatPayParam
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ChannelHttpRequest
import tech.chaosmin.framework.module.mgmt.service.ChannelHttpRequestService
import tech.chaosmin.framework.module.payment.domain.dataobject.PaymentTransaction
import tech.chaosmin.framework.module.payment.entity.wechat.request.NativePayReq
import tech.chaosmin.framework.module.payment.entity.wechat.request.NotifyReq
import tech.chaosmin.framework.module.payment.entity.wechat.response.NativePayResp
import tech.chaosmin.framework.module.payment.entity.wechat.response.NotifyResp
import tech.chaosmin.framework.module.payment.helper.convert.PaymentTransactionConvert
import tech.chaosmin.framework.module.payment.helper.mapper.PaymentTransactionMapper
import tech.chaosmin.framework.module.payment.service.PaymentTransactionService
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
open class WechatNativePayServiceImpl(
    private val wechatPayParam: WechatPayParam,
    private val paymentTransactionService: PaymentTransactionService,
    private val channelHttpRequestService: ChannelHttpRequestService
) : WechatNativePayService {
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

    private fun beforeRequest(req: NativePayReq): NativePayReq {
        // 补充微信支付信息
        req.appid = wechatPayParam.appId
        req.mchid = wechatPayParam.merchantId
        req.notify_url = wechatPayParam.notifyUrl
        return req
    }

    override fun readCertificates() {
        val uriBuilder = URIBuilder(wechatPayParam.url?.get(wechatPayParam.CERTIFICATES))
        val httpGet = HttpGet(uriBuilder.build())
        httpGet.addHeader("Accept", "application/json")
        getClient().execute(httpGet).use {
            val bodyAsString: String = EntityUtils.toString(it.entity)
            println(bodyAsString)
        }
    }

    override fun createOrder(req: NativePayReq): String {
        // 保存支付信息
        val transaction = PaymentTransactionConvert.INSTANCE.convert2Entity(req)
        transaction.status = TransactionStatusEnum.WAITING_FOR_PAY
        transaction.channel = TradeChannelEnum.WECHAT
        transaction.orderTime = Date()
        paymentTransactionService.save(PaymentTransactionMapper.INSTANCE.convert2DO(transaction))

        val url = wechatPayParam.url?.get(wechatPayParam.CREATE_NATIVE_ORDER)
        if (url.isNullOrBlank()) throw FrameworkException(ErrorCodeEnum.SYSTEM_ERROR.code, "微信支付配置[${wechatPayParam.CREATE_NATIVE_ORDER}]丢失")
        val httpPost = HttpPost(url)
        httpPost.addHeader("Accept", "application/json")
        httpPost.addHeader("Content-type", "application/json; charset=utf-8")
        val requestStr = JsonUtil.encode(beforeRequest(req))
        logger.info("wechat.native.create_order request_param: $requestStr")
        httpPost.entity = StringEntity(requestStr, "UTF-8")
        getClient().execute(httpPost).use {
            val bodyAsString = EntityUtils.toString(it.entity)
            channelHttpRequestService.save(ChannelHttpRequest().apply {
                this.httpMethod = HttpMethodEnum.POST.name
                this.httpUrl = url
                this.httpHeader = JsonUtil.encode(httpPost.allHeaders)
                this.requestBody = requestStr
                this.responseBody = bodyAsString
                logger.info("POST ${this.httpUrl}[${this.httpHeader}]\nrequest param: ${this.requestBody}\nresponse param: ${this.responseBody}")
            })
            logger.info("wechat.native.create_order response_param: $bodyAsString")
            try {
                val resp = JsonUtil.decode(bodyAsString, NativePayResp::class.java)
                // 设置状态为支付中并更新支付链接
                paymentTransactionService.updateByTradeNo(PaymentTransaction().apply {
                    this.status = TransactionStatusEnum.PAYING.getCode()
                    this.payUrl = resp?.code_url
                }, req.out_trade_no!!)
                // 生成二维码
                return QrCodeUtil.generateAsBase64(resp?.code_url, QrConfig.create(), ImgUtil.IMAGE_TYPE_PNG)
            } catch (e: Exception) {
                logger.error("调用微信Native下单接口失败", e)
                throw FrameworkException(ErrorCodeEnum.FAILURE.code, "调用微信Native下单接口失败")
            }
        }
    }

    override fun searchOrder() {
        TODO("Not yet implemented")
    }

    override fun closeOrder() {
        TODO("Not yet implemented")
    }

    override fun notifyPay(req: NotifyReq): NotifyResp {
        val ciphertext = req.resource?.ciphertext
        // 处理支付返回
        val result = NotifyResp().apply {
            this.code = ErrorCodeEnum.SUCCESS.code
            this.message = ErrorCodeEnum.SUCCESS.msg
        }
        channelHttpRequestService.save(ChannelHttpRequest().apply {
            this.httpMethod = HttpMethodEnum.POST.name
            this.httpUrl = "https://[localhost]:[port]/api/wechatpay/notify"
            this.requestBody = JsonUtil.encode(req)
            this.responseBody = JsonUtil.encode(result)
            this.extraInfo = JsonUtil.encode(ciphertext)
            logger.info("POST ${this.httpUrl}[${this.httpHeader}]\nrequest param: ${this.requestBody}\nresponse param: ${this.responseBody}")
        })
        return result
    }

    override fun refund() {
        TODO("Not yet implemented")
    }

    override fun notifyRefund() {
        TODO("Not yet implemented")
    }
}