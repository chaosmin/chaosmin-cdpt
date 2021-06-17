package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj

import java.util.*

/**
 * 大地保险保单取消响应体 <p>
 *
 * @author Romani min
 * @since 2021/6/17 21:40
 */
class DDCPResp {
    // 投保单号
    var proposalNo: String? = null

    // 业务来源属性
    var businessAttribute: String? = null

    // 请求业务类型
    var businessType: String? = null

    // 归属机构代码
    var orgCode: String? = null

    // 出单机构代码
    var issueOrgCode: String? = null

    // 出单员代码
    var issueUserCode: String? = null

    // 业务来源1
    var businessSourceCode: String? = null

    //  起保日期/起始生效日期, 支持时分秒
    var effectiveDate: Date? = null

    // 终保日期/终止失效日期, 支持时分秒
    var expiryDate: Date? = null

    // 投保日期
    var proposalDate: Date? = null

    // 定期结算方式
    var regularSettlementModeCode: String? = null

    // 定期结算日期
    var regularSettlementDate: Date? = null

    // 定期结算最晚结算日期
    var latestRegularSettleDate: Date? = null

    // 保费币种
    var premiumCurrencyCode: String? = null
}