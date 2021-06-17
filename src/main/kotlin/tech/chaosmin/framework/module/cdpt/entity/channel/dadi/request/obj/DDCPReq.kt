package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj

import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner.ChannelOpInfo
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner.EndorseMainInfo

/**
 * 大地保险保单取消(无退费)请求体 <p>
 *
 * @author Romani min
 * @since 2021/6/17 11:00
 */
class DDCPReq {
    var channelOpInfo: ChannelOpInfo? = null
    var endorseMainInfo: EndorseMainInfo? = null
}