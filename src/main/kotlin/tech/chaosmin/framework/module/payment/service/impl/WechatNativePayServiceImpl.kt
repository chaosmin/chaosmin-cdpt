package tech.chaosmin.framework.module.payment.service.impl

import cn.hutool.core.date.DateUtil
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
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import tech.chaosmin.framework.base.enums.*
import tech.chaosmin.framework.definition.WechatPayParam
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.enums.PayStatusEnum
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ChannelHttpRequest
import tech.chaosmin.framework.module.mgmt.service.ChannelHttpRequestService
import tech.chaosmin.framework.module.payment.domain.dataobject.PaymentTransaction
import tech.chaosmin.framework.module.payment.entity.PaymentNotifyEntity
import tech.chaosmin.framework.module.payment.entity.PaymentTransactionEntity
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
import tech.chaosmin.framework.module.payment.helper.mapper.PaymentTransactionMapper
import tech.chaosmin.framework.module.payment.service.PaymentTransactionService
import tech.chaosmin.framework.module.payment.service.WechatNativePayService
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
    private val paymentTransactionService: PaymentTransactionService,
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
        val transaction = PaymentTransactionEntity().apply {
            this.status = TransactionStatusEnum.WAITING_FOR_PAY
            this.amount = req.amount?.total?.toLong()
            this.channel = TradeChannelEnum.WECHAT
            this.outTradeNo = req.out_trade_no
            this.orderTime = Date()
        }
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

    override fun searchOrder(outTradeNo: String): NativeQueryResp {
        val url = wechatPayParam.url?.get(wechatPayParam.SEARCH_ORDER)?.replace("{out_trade_no}", outTradeNo)
        if (url.isNullOrBlank()) throw FrameworkException(ErrorCodeEnum.SYSTEM_ERROR.code, "微信支付配置[${wechatPayParam.SEARCH_ORDER}]丢失")
        val uriBuilder = URIBuilder(url)
        uriBuilder.setParameter("mchid", wechatPayParam.merchantId)
        val httpGet = HttpGet(uriBuilder.build())
        httpGet.addHeader("Accept", "application/json")
        httpGet.addHeader("Content-type", "application/json; charset=utf-8")
        logger.info("wechat.native.search_order request_param: $outTradeNo")
        getClient().execute(httpGet).use {
            val bodyAsString = EntityUtils.toString(it.entity)
            channelHttpRequestService.save(ChannelHttpRequest().apply {
                this.httpMethod = HttpMethodEnum.GET.name
                this.httpUrl = url
                this.httpHeader = JsonUtil.encode(httpGet.allHeaders)
                this.requestBody = JsonUtil.encode(NativeQueryReq().apply { this.transaction_id = outTradeNo })
                this.responseBody = bodyAsString
                logger.info("GET ${this.httpUrl}[${this.httpHeader}]\nrequest param: ${this.requestBody}\nresponse param: ${this.responseBody}")
            })
            logger.info("wechat.native.search_order response_param: $bodyAsString")
            try {
                return JsonUtil.decode(bodyAsString, NativeQueryResp::class.java) ?: NativeQueryResp()
            } catch (e: Exception) {
                logger.error("调用微信Native查单接口失败", e)
                throw FrameworkException(ErrorCodeEnum.FAILURE.code, "调用微信Native查单接口失败")
            }
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
            paymentTransactionService.updateByTradeNo(PaymentTransaction().apply {
                this.status = TransactionStatusEnum.CLOSE.getCode()
                this.closeTime = Date()
            }, outTradeNo)
            logger.info("wechat.native.close_order response_param: $bodyAsString")
        }
    }

    override fun notifyPay(req: NotifyReq): NotifyResp {
        val result = if (req.resource == null) {
            NotifyResp().apply {
                this.code = ErrorCodeEnum.PARAM_IS_NULL.code
                this.message = ExceptionMessageHelper.getExtMsg(ErrorCodeEnum.PARAM_IS_NULL.msg, "resource")
            }
        } else {
            val resource = req.resource!!
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
            logger.info("wechatpay decrypt AEAD_AES_256_GCM: $decryptStr")
            val payNotify = JsonUtil.decode(decryptStr, PayNotifyEntity::class.java)

            if (payNotify?.trade_state == WechatTradeStateEnum.SUCCESS) {
                paymentTransactionService.updateByTradeNo(PaymentTransaction().apply {
                    this.payer = payNotify.payer?.openid
                    this.status = TransactionStatusEnum.SUCCESS.getCode()
                    this.payTime = DateUtil.parseUTC(payNotify.success_time)
                }, payNotify.out_trade_no!!)
                val noticeMessage = PaymentNotifyEntity(payNotify.out_trade_no!!, PayStatusEnum.PAYMENT_SUCCESSFUL)
                rabbitTemplate.convertAndSend("payment", noticeMessage)
            } else {
                // TODO 邮件通知警告
                val noticeMessage = PaymentNotifyEntity(payNotify?.out_trade_no!!, PayStatusEnum.PAYMENT_FAILED)
                rabbitTemplate.convertAndSend("payment", noticeMessage)
                logger.error("微信支付失败! $payNotify")
            }

            // 处理支付返回
            NotifyResp().apply {
                this.code = ErrorCodeEnum.SUCCESS.code
                this.message = ErrorCodeEnum.SUCCESS.msg
            }
        }
        channelHttpRequestService.save(ChannelHttpRequest().apply {
            this.httpMethod = HttpMethodEnum.POST.name
            this.httpUrl = "https://[localhost]:[port]/api/wechatpay/notify"
            this.requestBody = JsonUtil.encode(req)
            this.responseBody = JsonUtil.encode(result)
            // this.extraInfo = decryptStr
            logger.info("POST ${this.httpUrl}[${this.httpHeader}]\nrequest param: ${this.requestBody}\nresponse param: ${this.responseBody}")
        })
        return result
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
            logger.info("wechat.native.refund response_param: $bodyAsString")
            try {
                return JsonUtil.decode(bodyAsString, NativeRefundResp::class.java) ?: NativeRefundResp()
            } catch (e: Exception) {
                logger.error("调用微信Native退款接口失败", e)
                throw FrameworkException(ErrorCodeEnum.FAILURE.code, "调用微信Native退款接口失败")
            }
        }
    }

    override fun notifyRefund(req: NotifyReq): NotifyResp {
        val result = if (req.resource == null) {
            NotifyResp().apply {
                this.code = ErrorCodeEnum.PARAM_IS_NULL.code
                this.message = ExceptionMessageHelper.getExtMsg(ErrorCodeEnum.PARAM_IS_NULL.msg, "resource")
            }
        } else {
            val resource = req.resource!!
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
            logger.info("wechatpay decrypt AEAD_AES_256_GCM: $decryptStr")
            val refundNotify = JsonUtil.decode(decryptStr, RefundNotifyEntity::class.java)

            // 退款成功时, 更新系统支付交易状态
            if (refundNotify?.refund_status == WechatRefundStatusEnum.SUCCESS) {
                paymentTransactionService.updateByTradeNo(PaymentTransaction().apply {
                    this.refundAmount = refundNotify.amount?.payer_refund
                    this.refundAccount = refundNotify.user_received_account
                    this.status = TransactionStatusEnum.REFUND.getCode()
                    this.refundTime = DateUtil.parseUTC(refundNotify.success_time)
                }, refundNotify.out_trade_no!!)
                val noticeMessage = PaymentNotifyEntity(refundNotify.out_trade_no!!, PayStatusEnum.REFUNDED)
                rabbitTemplate.convertAndSend("payment", noticeMessage)
            } else {
                // TODO 邮件通知警告
                logger.error("微信支付退款失败! $refundNotify")
            }

            // 处理支付返回
            NotifyResp().apply {
                this.code = ErrorCodeEnum.SUCCESS.code
                this.message = ErrorCodeEnum.SUCCESS.msg
            }
        }
        channelHttpRequestService.save(ChannelHttpRequest().apply {
            this.httpMethod = HttpMethodEnum.POST.name
            this.httpUrl = "https://[localhost]:[port]/api/wechatpay/refunds/notify"
            this.requestBody = JsonUtil.encode(req)
            this.responseBody = JsonUtil.encode(result)
            // this.extraInfo = decryptStr
            logger.info("POST ${this.httpUrl}[${this.httpHeader}]\nrequest param: ${this.requestBody}\nresponse param: ${this.responseBody}")
        })
        return result
    }
}