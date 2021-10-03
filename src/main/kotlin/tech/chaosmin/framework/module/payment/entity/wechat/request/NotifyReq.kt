package tech.chaosmin.framework.module.payment.entity.wechat.request

import io.swagger.annotations.ApiModel

/**
 * Native支付微信 请求体
 * @author Romani min
 * @since 2021/9/2 09:45
 */
@ApiModel("微信Native支付响应请求参数")
class NotifyReq {
    var id: String? = null
    var create_time: String? = null
    var resource_type: String? = null
    var event_type: String? = null
    var resource: Resource? = null
    var summary: String? = null

    inner class Resource {
        var algorithm: String? = null
        var ciphertext: String? = null
        var nonce: String? = null
        var original_type: String? = null
        var associated_data: String? = null
    }
}