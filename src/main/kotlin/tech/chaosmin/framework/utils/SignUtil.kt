package tech.chaosmin.framework.utils

import tech.chaosmin.framework.module.cdpt.entity.channel.request.BaseChannelReq

/**
 * @author Romani min
 * @since 2021/6/11 10:14
 */
object SignUtil {
    fun sortedParam(req: BaseChannelReq) {
        val params = JsonUtil.convert2Map(JsonUtil.encode(req))
        params.toSortedMap()
    }
}