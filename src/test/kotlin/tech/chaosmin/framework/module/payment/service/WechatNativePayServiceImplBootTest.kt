package tech.chaosmin.framework.module.payment.service

import org.junit.jupiter.api.Test
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.module.payment.entity.wechat.request.NativeReq
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/8/24 14:03
 */
class WechatNativePayServiceImplBootTest : BaseTestMain() {
    @Resource
    lateinit var wechatNativePayService: WechatNativePayService

    @Test
    fun getToken() {
        wechatNativePayService.token()
    }

    @Test
    fun createOrder() {
        val nativeReq = NativeReq().apply {
            this.description = "电子铅笔"
            this.amount = Amount().apply {
                this.total = 1
            }
        }
        wechatNativePayService.createOrder(nativeReq)
    }
}