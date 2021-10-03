/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * ThirdPartyRequestHandler.kt
 *
 * When requesting a third-party interface, the processor is used for packaging and logging.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.handler

import cn.hutool.http.Method
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ChannelHttpRequest
import tech.chaosmin.framework.module.mgmt.service.ChannelHttpRequestService
import tech.chaosmin.framework.utils.HttpUtil
import tech.chaosmin.framework.utils.JsonUtil

/**
 * @author Romani min
 * @since 2021/9/4 12:54
 */
@Component
open class ThirdPartyRequestHandler(private var channelHttpRequestService: ChannelHttpRequestService) {
    private val logger = LoggerFactory.getLogger(ThirdPartyRequestHandler::class.java)

    fun doRequest(method: Method, url: String, header: Map<String, String>, requestBody: Any): String {
        val uuid = System.currentTimeMillis()
        val requestHeadJson = JsonUtil.encode(header)
        logger.info("$uuid ${method.name} ${url} [${requestHeadJson}]")
        val requestJson = JsonUtil.encode(requestBody)
        logger.info("$uuid request param: $requestJson")
        val httpRequest = ChannelHttpRequest().apply {
            this.httpMethod = method.name
            this.httpUrl = url
            this.httpHeader = requestHeadJson
            this.requestBody = requestJson
        }
        try {
            val response = HttpUtil.doRequest(method, url, header, requestJson)
            val responseStatus = response.first
            val responseJson = response.second
            logger.info("$uuid response param: $responseJson")
            httpRequest.httpStatus = responseStatus.toString()
            httpRequest.responseBody = responseJson
            httpRequest.costTime = System.currentTimeMillis() - uuid
            return responseJson
        } catch (e: Exception) {
            httpRequest.httpStatus = "ERROR"
            httpRequest.responseBody = e.message
            httpRequest.costTime = System.currentTimeMillis() - uuid
            throw FrameworkException(ErrorCodeEnum.FAILED_TO_REQUEST.code, e, url)
        } finally {
            channelHttpRequestService.save(httpRequest)
        }
    }
}