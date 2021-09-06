/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PartnerInterfaceService.kt
 *
 * The third-party partner requests the abstract base class of the interface
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.outer

import cn.hutool.http.Method
import org.slf4j.LoggerFactory
import tech.chaosmin.framework.base.enums.PolicyProcessEnum
import tech.chaosmin.framework.module.cdpt.entity.channel.BaseChannelReq
import tech.chaosmin.framework.module.cdpt.logic.handler.ThirdPartyRequestHandler
import tech.chaosmin.framework.utils.JsonUtil

/**
 * @author Romani min
 * @since 2021/9/4 13:17
 */
abstract class PartnerInterfaceService(private val thirdPartyRequestHandler: ThirdPartyRequestHandler) {
    private val logger = LoggerFactory.getLogger(PartnerInterfaceService::class.java)

    // 获取请求信息(请求方式 + 请求地址)
    abstract fun getRequestInfo(process: PolicyProcessEnum): RequestDTO

    abstract fun header(process: PolicyProcessEnum, body: BaseChannelReq): Map<String, String>

    fun <T> request(process: PolicyProcessEnum, requestBody: BaseChannelReq, func: (String?) -> T): T? {
        val (method, server, `interface`) = getRequestInfo(process)
        val header = header(process, requestBody)
        val response = thirdPartyRequestHandler.doRequest(method, "${server}${`interface`}", header, requestBody)
        val result = func.invoke(response)
        logger.info("partner interface request result: ${JsonUtil.encode(result)}")
        return result
    }

    data class RequestDTO(val method: Method, val server: String, val `interface`: String)
}