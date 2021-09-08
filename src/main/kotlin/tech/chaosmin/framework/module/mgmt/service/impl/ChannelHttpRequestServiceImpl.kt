package tech.chaosmin.framework.module.mgmt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpRequestBase
import org.apache.http.util.EntityUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.mgmt.domain.dao.ChannelHttpRequestDAO
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ChannelHttpRequest
import tech.chaosmin.framework.module.mgmt.service.ChannelHttpRequestService
import tech.chaosmin.framework.utils.JsonUtil

/**
 * @author Romani min
 * @since 2021/6/21 15:15
 */
@Service
open class ChannelHttpRequestServiceImpl : ServiceImpl<ChannelHttpRequestDAO, ChannelHttpRequest>(), ChannelHttpRequestService {
    private val logger = LoggerFactory.getLogger(ChannelHttpRequestService::class.java)

    override fun <T> saveRequest(request: HttpRequestBase, requestStr: String, entity: HttpEntity, func: (String) -> T): T {
        val bodyAsString = EntityUtils.toString(entity)
        this.save(ChannelHttpRequest().apply {
            this.httpMethod = request.method
            this.httpUrl = request.uri.toString()
            this.httpHeader = JsonUtil.encode(request.allHeaders)
            this.requestBody = requestStr
            this.responseBody = bodyAsString
            logger.info(
                "${this.httpMethod} ${this.httpUrl} [${this.httpHeader}]\n" +
                        "request param: ${this.requestBody}\n" +
                        "response param: ${this.responseBody}"
            )
        })
        return func.invoke(bodyAsString)
    }
}