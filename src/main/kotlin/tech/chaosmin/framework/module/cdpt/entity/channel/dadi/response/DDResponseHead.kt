package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * @author Romani min
 * @since 2021/6/17 21:23
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class DDResponseHead {
    // code
    var appCode: String? = null

    // message
    var appMessage: String? = null

    // 接口状态
    var status: String? = null

    // 服务流水号
    var responseId: String? = null

    // 响应时间
    var responseTime: String? = null

    // 响应代码
    var resultCode: String? = null

    // 响应信息
    var resultMessage: String? = null
}