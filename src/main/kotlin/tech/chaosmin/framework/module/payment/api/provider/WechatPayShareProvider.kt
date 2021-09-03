package tech.chaosmin.framework.module.payment.api.provider

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.payment.api.WechatPayShareService
import tech.chaosmin.framework.module.payment.entity.wechat.request.NativePayReq
import tech.chaosmin.framework.module.payment.entity.wechat.request.NativeRefundReq
import tech.chaosmin.framework.module.payment.entity.wechat.request.NotifyReq
import tech.chaosmin.framework.module.payment.entity.wechat.response.NativeQueryResp
import tech.chaosmin.framework.module.payment.entity.wechat.response.NativeRefundResp
import tech.chaosmin.framework.module.payment.entity.wechat.response.NotifyResp
import tech.chaosmin.framework.module.payment.service.WechatNativePayService
import tech.chaosmin.framework.utils.JsonUtil

/**
 * @author Romani min
 * @since 2021/8/25 14:35
 */
@RestController
class WechatPayShareProvider(private val wechatpayNativePayService: WechatNativePayService) : WechatPayShareService {
    private val logger = LoggerFactory.getLogger(WechatPayShareService::class.java)

    override fun createTrade(req: NativePayReq): RestResult<String> {
        logger.info("WechatPayShareProvider.native.request: ${JsonUtil.encode(req)}")
        val resp = wechatpayNativePayService.createOrder(req)
        logger.info("WechatPayShareProvider.native.request: ${JsonUtil.encode(resp)}")
        return RestResultExt.successRestResult(data = resp)
    }

    override fun searchTrade(orderNo: String): RestResult<NativeQueryResp> {
        logger.info("WechatPayShareProvider.search.request: $orderNo")
        val resp = wechatpayNativePayService.searchOrder(orderNo)
        logger.info("WechatPayShareProvider.search.request: ${JsonUtil.encode(resp)}")
        return RestResultExt.successRestResult(data = resp)
    }

    override fun closeTrade(orderNo: String): RestResult<Void> {
        logger.info("WechatPayShareProvider.close.request: $orderNo")
        wechatpayNativePayService.closeOrder(orderNo)
        return RestResultExt.successRestResult()
    }

    override fun notify(req: NotifyReq): NotifyResp {
        logger.info("WechatPayShareProvider.notify.request: ${JsonUtil.encode(req)}")
        val resp = wechatpayNativePayService.notifyPay(req)
        logger.info("WechatPayShareProvider.notify.response: ${JsonUtil.encode(resp)}")
        return resp
    }

    override fun refund(orderNo: String, req: NativeRefundReq): RestResult<NativeRefundResp> {
        logger.info("WechatPayShareProvider.refund.request: ${JsonUtil.encode(req)}")
        val resp = wechatpayNativePayService.refund(req)
        logger.info("WechatPayShareProvider.refund.response: ${JsonUtil.encode(resp)}")
        return RestResultExt.successRestResult(data = resp)
    }

    override fun refundNotify(req: NotifyReq): NotifyResp {
        logger.info("WechatPayShareProvider.refundNotify.request: ${JsonUtil.encode(req)}")
        val resp = wechatpayNativePayService.notifyRefund(req)
        logger.info("WechatPayShareProvider.refundNotify.response: ${JsonUtil.encode(resp)}")
        return resp
    }
}