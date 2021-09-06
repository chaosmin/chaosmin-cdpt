package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj

import com.fasterxml.jackson.annotation.JsonInclude
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner.ChannelOpInfo
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner.Policy

/**
 * 大地保险保单核保请求体 <p>
 * <p>
 * @author Romani min
 * @since 2021/6/17 10:36
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
class DDUReq {
    var channelOpInfoDTO: ChannelOpInfo? = null
    var policyDTO: Policy? = null
}