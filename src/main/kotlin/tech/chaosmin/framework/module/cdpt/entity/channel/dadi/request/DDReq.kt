package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request

import com.fasterxml.jackson.annotation.JsonInclude
import tech.chaosmin.framework.module.cdpt.entity.channel.BaseChannelReq

/**
 * 大地保险对接请求体 <p>
 * <p>
 *
 * @author Romani min
 * @since 2021/6/16 10:58
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class DDReq<T> : BaseChannelReq() {
    var requestHead: DDRequestHead? = null
    var requestBody: T? = null
}