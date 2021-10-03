package tech.chaosmin.framework.module.payment.service

import org.junit.jupiter.api.Test
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.module.payment.entity.wechat.request.NativePayReq
import tech.chaosmin.framework.module.payment.logic.service.WechatNativePayService
import java.util.*
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/8/24 14:03
 */
class WechatNativePayServiceImplBootTest : BaseTestMain() {
    @Resource
    lateinit var wechatNativePayService: WechatNativePayService

    @Test
    fun createOrder() {
        val nativeReq = NativePayReq().apply {
            this.out_trade_no = UUID.randomUUID().toString().replace("-", "").take(16)
            this.description = "电子铅笔"
            this.amount = Amount().apply {
                this.total = 1
            }
        }
        wechatNativePayService.createOrder(nativeReq)
    }
}