package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response

import tech.chaosmin.framework.module.cdpt.entity.channel.BaseChannelResp

/**
 * 大地保险对接响应体 <p>
 * <p>
 *
 * @author Romani min
 * @since 2021/6/16 11:01
 */
class DDResp<T> : BaseChannelResp() {
    var responseHead: DDResponseHead? = null
    var responseBody: T? = null
}