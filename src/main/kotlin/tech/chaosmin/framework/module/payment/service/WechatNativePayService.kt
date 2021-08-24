package tech.chaosmin.framework.module.payment.service

import tech.chaosmin.framework.module.payment.entity.wechat.request.NativeReq
import tech.chaosmin.framework.module.payment.entity.wechat.response.NativeResp

/**
 * 微信支付 外部服务
 *
 * @author Romani min
 * @since 2021/8/23 16:39
 */
interface WechatNativePayService {
    fun token()

    fun createOrder(req: NativeReq): NativeResp

    fun searchOrder()

    fun closeOrder()

    fun notifyPay()

    fun refund()

    fun notifyRefund()
}