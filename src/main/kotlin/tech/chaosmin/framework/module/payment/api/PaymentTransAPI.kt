/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PaymentTrasactionService.java
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.payment.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.payment.entity.resp.PaymentTransResp

/**
 * @author Romani min
 * @since 2021/9/22 11:02
 */
@Api(tags = ["支付交易记录操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/payment-trans")
interface PaymentTransAPI {
    @GetMapping("/{orderNo}")
    fun searchTrade(@PathVariable("orderNo") orderNo: String): RestResult<PaymentTransResp>
}