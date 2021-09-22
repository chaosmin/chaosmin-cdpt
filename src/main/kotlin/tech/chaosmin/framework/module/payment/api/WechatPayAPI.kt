package tech.chaosmin.framework.module.payment.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.payment.entity.wechat.request.NativePayReq
import tech.chaosmin.framework.module.payment.entity.wechat.request.NativeRefundReq
import tech.chaosmin.framework.module.payment.entity.wechat.request.NotifyReq
import tech.chaosmin.framework.module.payment.entity.wechat.response.NativeQueryResp
import tech.chaosmin.framework.module.payment.entity.wechat.response.NativeRefundResp
import tech.chaosmin.framework.module.payment.entity.wechat.response.NotifyResp

/**
 * @author Romani min
 * @since 2021/8/25 14:32
 */
@Api(tags = ["微信支付操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/wechatpay")
interface WechatPayAPI {
    @PostMapping
    fun createTrade(@RequestBody req: NativePayReq): RestResult<String>

    @GetMapping("/{orderNo}")
    fun searchTrade(@PathVariable("orderNo") orderNo: String): RestResult<NativeQueryResp>

    @PutMapping("/{orderNo}/close")
    fun closeTrade(@PathVariable("orderNo") orderNo: String): RestResult<Void>

    @PutMapping("/{orderNo}/refund")
    fun refund(@PathVariable("orderNo") orderNo: String, @RequestBody req: NativeRefundReq): RestResult<NativeRefundResp>

    @PostMapping("/notify/pay")
    fun notify(@RequestBody req: NotifyReq): NotifyResp

    @PostMapping("/notify/refunds")
    fun refundNotify(@RequestBody req: NotifyReq): NotifyResp
}