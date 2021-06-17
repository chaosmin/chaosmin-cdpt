package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request

import cn.hutool.core.date.DateUtil
import java.io.Serializable
import java.util.*

/**
 * @author Romani min
 * @since 2021/6/17 10:43
 */
class DDRequestHead(orderNo: String) : Serializable {
    // 流程流水号, 服务消费方用于唯一标识一系列服务的流水号，建议使用GUID
    var seqNo: String = orderNo

    // 服务流水号, 服务消费方用于唯一标识一次服务交互，建议使用GUID
    var requestId: String = UUID.randomUUID().toString()

    // 请求时间 yyyy-MM-ddThh:mm:ss
    var requestTime: String = "${DateUtil.formatDate(Date())}T${DateUtil.formatTime(Date())}"

    // 服务版本
    var version: String? = null

}