package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import java.util.*

/**
 * 大地保险渠道信息 <p>
 *
 * @author Romani min
 * @since 2021/6/17 11:11
 */
class ChannelOpInfo {
    // TODO 渠道代码
    var channelCode: String = "E00149-002"

    // 渠道名称
    var channelName: String? = null

    // 渠道机构代码
    var channelComCode: String? = null

    // 渠道机构名称
    var channelComName: String? = null

    // 渠道产品代码
    var channelProductCode: String? = null

    // 渠道操作员代码
    var operatorCode: String? = null

    // 渠道交易代码
    var trxCode: String? = null

    // 渠道交易流水号
    var channelSeqNo: String? = null

    // 渠道地区代码
    var channelAreaCode: String? = null

    // 渠道交易日期
    var trxDate: Date? = null

    // 渠道关联单号
    var channelRelationNo: String? = null

    // 拓展字段
    var extMessage: String? = null
}