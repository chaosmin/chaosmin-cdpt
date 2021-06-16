package tech.chaosmin.framework.module.cdpt.entity.channel.request

import java.util.*

/**
 * 永诚保险对接请求体 <p>
 * <p>
 *
 * @author Romani min
 * @since 2021/6/11 10:17
 */
open class YCReq : BaseChannelReq() {
    // 服务名.方法名
    var action: String? = null

    // 密钥 ID secretId
    var appKey: String? = null

    // 当前时间戳
    var timestamp: Long = Date().time

    // 随机正整数(至少8位，保证全局唯一)
    var nonce: Long? = null

    // 签名方式，确定具体的签名算法。如果不为HmacSHA256，否则为HmacSHA1
    var signAlgo: String? = null

    // 服务默认的参数名，参数值一般为Json字符串，视具体的服务
    var zdata: String? = null

    // 签名串，根据签名规则生成
    var sign: String? = null
}