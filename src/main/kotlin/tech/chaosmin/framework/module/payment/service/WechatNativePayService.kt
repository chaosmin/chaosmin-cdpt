package tech.chaosmin.framework.module.payment.service

import tech.chaosmin.framework.module.payment.entity.wechat.request.NativePayReq
import tech.chaosmin.framework.module.payment.entity.wechat.request.NativeRefundReq
import tech.chaosmin.framework.module.payment.entity.wechat.request.NotifyReq
import tech.chaosmin.framework.module.payment.entity.wechat.response.NativeQueryResp
import tech.chaosmin.framework.module.payment.entity.wechat.response.NativeRefundResp
import tech.chaosmin.framework.module.payment.entity.wechat.response.NotifyResp

/**
 * 微信支付 外部服务
 *
 * @author Romani min
 * @since 2021/8/23 16:39
 */
interface WechatNativePayService {
    fun readCertificates()

    fun createOrder(req: NativePayReq): String

    fun searchOrder(outTradeNo: String): NativeQueryResp

    fun closeOrder(outTradeNo: String)

    fun notifyPay(req: NotifyReq): NotifyResp

    fun refund(req: NativeRefundReq): NativeRefundResp

    fun notifyRefund(req: NotifyReq): NotifyResp
}