package tech.chaosmin.framework.module.mgmt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO

/**
 * 渠道请求记录 <p>
 *
 * @author Romani min
 * @since 2021/6/21 15:11
 */
open class ChannelHttpRequest(id: Long? = null) : BaseDO(id, 0) {
    // 请求方式
    var httpMethod: String? = null

    // 请求链接
    var httpUrl: String? = null

    // 请求头
    var httpHeader: String? = null

    // 请求体
    var requestBody: String? = null

    // 响应体
    var responseBody: String? = null

    // 请求结果
    var httpStatus: String? = null

    // 请求耗时(ms)
    var costTime: Long? = null
}