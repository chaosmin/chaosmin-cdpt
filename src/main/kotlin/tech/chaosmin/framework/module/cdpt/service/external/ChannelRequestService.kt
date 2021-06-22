package tech.chaosmin.framework.module.cdpt.service.external

import cn.hutool.http.Method
import org.slf4j.LoggerFactory
import tech.chaosmin.framework.module.cdpt.domain.enums.PolicyProcessEnum
import tech.chaosmin.framework.module.cdpt.entity.channel.BaseChannelReq
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ChannelHttpRequest
import tech.chaosmin.framework.module.mgmt.domain.enums.HttpMethodEnum
import tech.chaosmin.framework.module.mgmt.service.ChannelHttpRequestService
import tech.chaosmin.framework.utils.HttpUtil
import tech.chaosmin.framework.utils.JsonUtil

/**
 * @author Romani min
 * @since 2021/6/21 14:58
 */
abstract class ChannelRequestService(private var channelHttpRequestService: ChannelHttpRequestService) {
    private val logger = LoggerFactory.getLogger(ChannelRequestService::class.java)

    abstract val server: String
    abstract val requestUrl: Map<PolicyProcessEnum, String>

    abstract fun header(url: String, body: BaseChannelReq): Map<String, String>

    fun <T> request(process: PolicyProcessEnum, requestBody: BaseChannelReq, func: (String?) -> T): T? {
        var result: T?
        val url = requestUrl[process]!!
        val header = header(url, requestBody)
        channelHttpRequestService.save(ChannelHttpRequest().apply {
            this.httpMethod = HttpMethodEnum.POST.name
            this.httpUrl = "${server}${url}"
            this.httpHeader = JsonUtil.encode(header)
            this.requestBody = JsonUtil.encode(requestBody)
            this.responseBody = HttpUtil.doRequest(Method.POST, this.httpUrl!!, header, this.requestBody!!)
            logger.info("POST ${this.httpUrl}[${this.httpHeader}]\nrequest param: ${this.requestBody}\nresponse param: ${this.responseBody}")
            result = func.invoke(this.responseBody)
        })
        return result
    }
}