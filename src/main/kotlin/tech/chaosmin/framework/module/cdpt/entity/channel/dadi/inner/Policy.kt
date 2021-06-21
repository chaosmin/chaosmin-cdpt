package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

/**
 * @author Romani min
 * @since 2021/6/17 11:11
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class Policy {
    // 单证流水号
    var printNo: String? = null

    // 投保单号/保险单号
    var businessNo: String? = null

    // 原渠道交易流水号
    var originTradeSerialNo: String? = null

    // 业务类型(非见费出单不需要填写)
    var certiType: String? = null

    // 当前核保人员代码(非见费出单不需要填写)
    var currUwCode: String? = null

    // 核保通过时间(非见费出单不需要填写)
    var underwritingDate: Date? = null

    // 支付方式
    var payType: String? = null

    // 申请系统
    var systemCode: String? = null
}