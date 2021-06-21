package tech.chaosmin.framework.configuration.channel

/**
 * 保险配置加载类 <p>
 *
 * @author Romani min
 * @since 2021/6/21 13:27
 */
open class InsurerProperties {
    // 服务请求地址
    var server: String = ""
    var accessKey: String = ""
    var securityKey: String = ""
    var calculatePremiumUrl: String = ""
    var underwritingUrl: String = ""
    var cancelPolicyUrl: String = ""
    var refundPolicyUrl: String = ""
}