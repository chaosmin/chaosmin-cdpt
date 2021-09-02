package tech.chaosmin.framework.module.payment.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.module.payment.entity.wechat.request.NotifyReq
import tech.chaosmin.framework.module.payment.entity.wechat.response.NotifyResp

/**
 * @author Romani min
 * @since 2021/8/25 14:32
 */
@Api(tags = ["微信支付操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/wechatpay")
interface WechatPayShareService {
    @PostMapping("/notify")
    fun notify(@RequestBody req: NotifyReq): NotifyResp
}