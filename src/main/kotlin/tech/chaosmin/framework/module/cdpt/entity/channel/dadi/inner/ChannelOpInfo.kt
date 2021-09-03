package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*

/**
 * 大地保险渠道信息 <p>
 *
 * @author Romani min
 * @since 2021/6/17 11:11
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class ChannelOpInfo {
    var channelCode: String = "E00149-002"

    // 渠道名称
    var channelName: String? = null

    // 渠道机构代码
    var channelComCode: String = "31011500"

    // 渠道机构名称
    var channelComName: String? = null

    // 渠道产品代码
    var channelProductCode: String? = null

    // 渠道操作员代码
    var operatorCode: String? = null

    // 渠道交易代码
    var trxCode: String? = null

    // 渠道交易流水号 幂等键 必填
    var channelSeqNo: String? = null

    // 渠道地区代码
    var channelAreaCode: String? = null

    // 渠道交易日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    var trxDate: Date? = null

    // 渠道关联单号
    var channelRelationNo: String? = null

    // 拓展字段
    var extMessage: String? = null
}