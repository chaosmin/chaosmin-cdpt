package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 大地保险保单核保响应体 <p>
 *
 * @author Romani min
 * @since 2021/6/17 21:40
 */
@JsonIgnoreProperties(value = ["epolicyURL"], ignoreUnknown = true)
class DDUResp {
    // 移动端支付链接
    var mobilePaymentLink: String? = null

    // PC端支付链接
    var pcPaymentLink: String? = null

    // 支付号
    var payApplyNo: String? = null

    // 支付验证码
    var identityCode: String? = null

    // 总保费
    var sumPremium: String? = null

    // [HAS] 是否能下载电子保单
    @JsonProperty("isDownloadPolicy")
    var isDownloadPolicy: String? = null

    // [HAS] 保单号
    var policyNo: String? = null

    // [HAS] 电子保单/批单链接
    @JsonProperty("ePolicyURL")
    var ePolicyURL: String? = null
}