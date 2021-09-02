package tech.chaosmin.framework.definition

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author Romani min
 * @since 2021/8/24 14:38
 */
@Component
@ConfigurationProperties("payment.wechat")
@EnableConfigurationProperties(WechatPayParam::class)
object WechatPayParam {
    const val CERTIFICATES = "certificates"
    const val CREATE_NATIVE_ORDER = "createNativeOrder"
    const val SEARCH_ORDER = "searchOrder"
    const val CLOSE_ORDER = "closeOrder"
    const val REFUND = "refund"

    var privateKey: String? = null
    var appId: String? = null
    var merchantId: String? = null
    var serialNumber: String? = null
    var v3Key: String? = null

    // 回调通知接口
    var notifyUrl: String? = null
    var refundNotifyUrl: String? = null

    // 支付渠道请求链接
    var url: Map<String, String>? = null
}