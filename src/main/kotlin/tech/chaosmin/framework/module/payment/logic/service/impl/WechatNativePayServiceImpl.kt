package tech.chaosmin.framework.module.payment.logic.service.impl

import cn.hutool.core.date.DateUtil
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
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import tech.chaosmin.framework.base.enums.*
import tech.chaosmin.framework.definition.WechatPayParam
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.enums.PayStatusEnum
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ChannelHttpRequest
import tech.chaosmin.framework.module.mgmt.service.ChannelHttpRequestService
import tech.chaosmin.framework.module.payment.domain.dataobject.PaymentTransaction
import tech.chaosmin.framework.module.payment.domain.service.PaymentTransService
import tech.chaosmin.framework.module.payment.entity.PaymentNotifyEntity
import tech.chaosmin.framework.module.payment.entity.PaymentTransEntity
import tech.chaosmin.framework.module.payment.entity.wechat.PayNotifyEntity
import tech.chaosmin.framework.module.payment.entity.wechat.RefundNotifyEntity
import tech.chaosmin.framework.module.payment.entity.wechat.request.NativePayReq
import tech.chaosmin.framework.module.payment.entity.wechat.request.NativeQueryReq
import tech.chaosmin.framework.module.payment.entity.wechat.request.NativeRefundReq
import tech.chaosmin.framework.module.payment.entity.wechat.request.NotifyReq
import tech.chaosmin.framework.module.payment.entity.wechat.response.NativePayResp
import tech.chaosmin.framework.module.payment.entity.wechat.response.NativeQueryResp
import tech.chaosmin.framework.module.payment.entity.wechat.response.NativeRefundResp
import tech.chaosmin.framework.module.payment.entity.wechat.response.NotifyResp
import tech.chaosmin.framework.module.payment.logic.convert.PaymentTransMapper
import tech.chaosmin.framework.module.payment.logic.service.WechatNativePayService
import tech.chaosmin.framework.utils.AESUtil
import tech.chaosmin.framework.utils.ExceptionMessageHelper
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
    private val paymentTransService: PaymentTransService,
    private val channelHttpRequestService: ChannelHttpRequestService,
    private val rabbitTemplate: RabbitTemplate
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

        // TODO 金额校验

        if (wechatPayParam.mock != null && wechatPayParam.mock!!) {
            // 如果开启了mock开关, 则将支付金额设置为1
            req.amount?.total = 1
        }
        return req
    }

    private fun beforeRequest(req: NativeRefundReq): NativeRefundReq {
        // 补充微信支付信息
        req.notify_url = wechatPayParam.refundNotifyUrl
        req.amount.currency = "CNY"

        if (wechatPayParam.mock != null && wechatPayParam.mock!!) {
            // 如果开启了mock开关, 则将退款金额设置为1
            req.amount.refund = 1
            req.amount.total = 1
        }
        return req
    }

    /**
     * 微信支付 字符串解密方法
     */
    private fun decrypt(resource: NotifyReq.Resource): String {
        val associatedData = resource.associated_data
        val nonce = resource.nonce
        val ciphertext = resource.ciphertext
        if (nonce.isNullOrBlank() || ciphertext.isNullOrBlank()) {
            NotifyResp().apply {
                this.code = ErrorCodeEnum.PARAM_IS_NULL.code
                this.message = ExceptionMessageHelper.getExtMsg(ErrorCodeEnum.PARAM_IS_NULL.msg, "resource.nonce or resource.ciphertext")
            }
        }
        val decryptStr = AESUtil.decryptAES256GCM(wechatPayParam.v3Key!!, associatedData, nonce!!, ciphertext!!)
        logger.info("wechatpay decrypt str AEAD_AES_256_GCM: $decryptStr")
        return decryptStr
    }

    /**
     * 证书自动更新方法
     */
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
        val transaction = PaymentTransEntity().apply {
            this.status = TransactionStatusEnum.WAITING_FOR_PAY
            this.amount = req.amount?.total?.toLong()
            this.channel = TradeChannelEnum.WECHAT
            this.outTradeNo = req.out_trade_no
            this.description = req.description
            this.orderTime = Date()
        }
        paymentTransService.save(PaymentTransMapper.INSTANCE.toDO(transaction))

        val url = wechatPayParam.url?.get(wechatPayParam.CREATE_NATIVE_ORDER)
        if (url.isNullOrBlank()) throw FrameworkException(ErrorCodeEnum.SYSTEM_ERROR.code, "微信支付配置[${wechatPayParam.CREATE_NATIVE_ORDER}]丢失")
        val httpPost = HttpPost(url)
        httpPost.addHeader("Accept", "application/json")
        httpPost.addHeader("Content-type", "application/json; charset=utf-8")
        val requestStr = JsonUtil.encode(beforeRequest(req))
        logger.info("wechat.native.create_order request_param: $requestStr")
        httpPost.entity = StringEntity(requestStr, "UTF-8")
        try {
            getClient().execute(httpPost).use {
                val resp = channelHttpRequestService.saveRequest(httpPost, requestStr, it.entity) { str ->
                    JsonUtil.decode(str, NativePayResp::class.java)
                }
                // 设置状态为支付中并更新支付链接
                paymentTransService.updateByTradeNo(PaymentTransaction().apply {
                    this.status = TransactionStatusEnum.PAYING.getCode()
                    this.payUrl = resp?.code_url
                }, req.out_trade_no!!)
                // 生成二维码
                return resp?.code_url ?: ""
            }
        } catch (e: Exception) {
            logger.error("调用微信Native下单接口失败", e)
            throw FrameworkException(ErrorCodeEnum.FAILURE.code, "调用微信Native下单接口失败")
        }
    }

    override fun searchOrder(outTradeNo: String): NativeQueryResp {
        val url = wechatPayParam.url?.get(wechatPayParam.SEARCH_ORDER)?.replace("{out_trade_no}", outTradeNo)
        if (url.isNullOrBlank()) throw FrameworkException(ErrorCodeEnum.SYSTEM_ERROR.code, "微信支付配置[${wechatPayParam.SEARCH_ORDER}]丢失")
        val uriBuilder = URIBuilder(url)
        uriBuilder.setParameter("mchid", wechatPayParam.merchantId)
        val httpGet = HttpGet(uriBuilder.build())
        httpGet.addHeader("Accept", "application/json")
        httpGet.addHeader("Content-type", "application/json; charset=utf-8")
        logger.info("wechat.native.search_order request_param: $outTradeNo")
        try {
            getClient().execute(httpGet).use {
                val resp = channelHttpRequestService.saveRequest(httpGet, "", it.entity) { str ->
                    JsonUtil.decode(str, NativeQueryResp::class.java)
                }
                return resp ?: NativeQueryResp()
            }
        } catch (e: Exception) {
            logger.error("调用微信Native查单接口失败", e)
            throw FrameworkException(ErrorCodeEnum.FAILURE.code, "调用微信Native查单接口失败")
        }
    }

    override fun closeOrder(outTradeNo: String) {
        val url = wechatPayParam.url?.get(wechatPayParam.CLOSE_ORDER)?.replace("{out_trade_no}", outTradeNo)
        if (url.isNullOrBlank()) throw FrameworkException(ErrorCodeEnum.SYSTEM_ERROR.code, "微信支付配置[${wechatPayParam.CLOSE_ORDER}]丢失")
        val httpPost = HttpPost(url)
        httpPost.addHeader("Accept", "application/json")
        httpPost.addHeader("Content-type", "application/json; charset=utf-8")
        val req = NativeQueryReq().apply {
            this.mchid = wechatPayParam.merchantId
        }
        val requestStr = JsonUtil.encode(req)
        logger.info("wechat.native.close_order request_param: $requestStr")
        httpPost.entity = StringEntity(requestStr, "UTF-8")
        try {
            getClient().execute(httpPost).use {
                channelHttpRequestService.saveRequest(httpPost, requestStr, it.entity) {}
                paymentTransService.updateByTradeNo(PaymentTransaction().apply {
                    this.status = TransactionStatusEnum.CLOSE.getCode()
                    this.closeTime = Date()
                }, outTradeNo)
            }
        } catch (e: Exception) {
            logger.error("调用微信Native取消接口失败", e)
            throw FrameworkException(ErrorCodeEnum.FAILURE.code, "调用微信Native取消接口失败")
        }
    }

    override fun notifyPay(req: NotifyReq): NotifyResp {
        channelHttpRequestService.save(ChannelHttpRequest().apply {
            this.httpMethod = HttpMethodEnum.POST.name
            this.httpUrl = "https://[localhost]:[port]/api/wechatpay/notify"
            this.requestBody = JsonUtil.encode(req)
            this.responseBody = JsonUtil.encode(req)
            logger.info("POST ${this.httpUrl}[${this.httpHeader}]\nrequest param: ${this.requestBody}\nresponse param: ${this.responseBody}")
        })
        if (req.resource == null) {
            return NotifyResp.failed(ErrorCodeEnum.PARAM_IS_NULL, "resource is null")
        } else {
            val decryptStr = decrypt(req.resource!!)
            val payNotify = JsonUtil.decode(decryptStr, PayNotifyEntity::class.java)
            if (payNotify?.trade_state == WechatTradeStateEnum.SUCCESS) {
                paymentTransService.updateByTradeNo(PaymentTransaction().apply {
                    this.payTime = DateUtil.parseUTC(payNotify.success_time)
                    this.status = TransactionStatusEnum.SUCCESS.getCode()
                    this.transactionId = payNotify.transaction_id
                    this.payer = payNotify.payer?.openid
                }, payNotify.out_trade_no!!)
                val noticeMessage = PaymentNotifyEntity(payNotify.out_trade_no!!, PayStatusEnum.PAYMENT_SUCCESSFUL)
                logger.info("Send message to queue [payment]: ${JsonUtil.encode(noticeMessage)}")
                rabbitTemplate.convertAndSend("payment", noticeMessage)
            } else {
                // TODO 邮件通知警告
                val noticeMessage = PaymentNotifyEntity(payNotify?.out_trade_no!!, PayStatusEnum.PAYMENT_FAILED)
                rabbitTemplate.convertAndSend("payment", noticeMessage)
                logger.error("微信支付失败! $decryptStr")
            }
            // 处理支付返回
            return NotifyResp.success()
        }
    }

    override fun refund(req: NativeRefundReq): NativeRefundResp {
        val url = wechatPayParam.url?.get(wechatPayParam.REFUND)
        if (url.isNullOrBlank()) throw FrameworkException(ErrorCodeEnum.SYSTEM_ERROR.code, "微信支付配置[${wechatPayParam.REFUND}]丢失")
        val httpPost = HttpPost(url)
        httpPost.addHeader("Accept", "application/json")
        httpPost.addHeader("Content-type", "application/json; charset=utf-8")
        val requestStr = JsonUtil.encode(beforeRequest(req))
        logger.info("wechat.native.refund request_param: $requestStr")
        httpPost.entity = StringEntity(requestStr, "UTF-8")
        try {
            getClient().execute(httpPost).use {
                val resp = channelHttpRequestService.saveRequest(httpPost, requestStr, it.entity) { str ->
                    JsonUtil.decode(str, NativeRefundResp::class.java)
                }
                return resp ?: NativeRefundResp()
            }
        } catch (e: Exception) {
            logger.error("调用微信Native退款接口失败", e)
            throw FrameworkException(ErrorCodeEnum.FAILURE.code, "调用微信Native退款接口失败")
        }
    }

    override fun notifyRefund(req: NotifyReq): NotifyResp {
        channelHttpRequestService.save(ChannelHttpRequest().apply {
            this.httpMethod = HttpMethodEnum.POST.name
            this.httpUrl = "https://[localhost]:[port]/api/wechatpay/refunds/notify"
            this.requestBody = JsonUtil.encode(req)
            this.responseBody = JsonUtil.encode(req)
            logger.info("POST ${this.httpUrl}[${this.httpHeader}]\nrequest param: ${this.requestBody}\nresponse param: ${this.responseBody}")
        })
        if (req.resource == null) {
            return NotifyResp.failed(ErrorCodeEnum.PARAM_IS_NULL, "resource is null")
        } else {
            val decryptStr = decrypt(req.resource!!)
            val refundNotify = JsonUtil.decode(decryptStr, RefundNotifyEntity::class.java)

            // 退款成功时, 更新系统支付交易状态
            if (refundNotify?.refund_status == WechatRefundStatusEnum.SUCCESS) {
                paymentTransService.updateByTradeNo(PaymentTransaction().apply {
                    this.refundAmount = refundNotify.amount?.payer_refund
                    this.refundAccount = refundNotify.user_received_account
                    this.status = TransactionStatusEnum.REFUND.getCode()
                    this.refundTime = DateUtil.parseUTC(refundNotify.success_time)
                }, refundNotify.out_trade_no!!)
                val noticeMessage = PaymentNotifyEntity(refundNotify.out_trade_no!!, PayStatusEnum.REFUNDED)
                logger.info("Send message to queue [payment]: ${JsonUtil.encode(noticeMessage)}")
                rabbitTemplate.convertAndSend("payment", noticeMessage)
            } else {
                // TODO 邮件通知警告
                logger.error("微信支付退款失败! $decryptStr")
            }
            // 处理支付返回
            return NotifyResp.success()
        }
    }
}