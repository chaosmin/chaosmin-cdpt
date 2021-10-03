package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj

import com.fasterxml.jackson.annotation.JsonInclude
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner.*
import java.math.BigDecimal
import java.util.*

/**
 * 大地保险保费试算请求体 <p>
 *
 * @author Romani min
 * @since 2021/6/17 11:01
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
class DDCReq {
    // 投保单号
    var proposalNo: String? = null

    // 原保单号(续保必填)
    var historyPolicyNo: String? = null

    // 是否自动续保(Y/N)
    var ifAutoRenewal: String? = null

    // 业务来源属性
    var businessAttribute: String? = null

    // 保单性质（团单标记，区分个团）
    var policyNature: String? = null

    // 分户类型(被保险人清单类型)
    var insuredListType: String? = null

    // 产品代码
    var productCode: String? = null

    // 请求业务类型 1-新保、2-修改、3-从联单生成、4-续保，默认为1
    var businessType: String? = null

    // 归属机构代码
    var orgCode: String? = null

    // 出单机构代码
    var issueOrgCode: String? = null

    // 出单员代码
    var issueUserCode: String? = null

    // 出单点地址
    var issueAddress: String? = null

    // 业务来源1 对应归属经办人的业务来源，存放末级的业务来源
    var businessSourceCode: String? = null

    // 业务来源2 对应归属经办人2（业务风险分类）的业务来源生成，非前台录入
    var businessSource2Code: String? = null

    // 业务编码(RepairChannelCode)/Q码
    var repairChannelCode: String? = null

    // 起保日期/起始生效日期,支持时分秒
    var effectiveDate: Date? = null

    // 终保日期/终止失效日期,支持时分秒
    var expiryDate: Date? = null

    // 投保日期
    var proposalDate: Date? = null

    // 定期结算方式
    var regularSettlementModeCode: String? = null

    // 是否定期结算标识
    var isRegularSettlement: String? = null

    // 定期结算日期
    var regularSettlementDate: Date? = null

    // 定期结算最晚结算日期
    var latestRegularSettleDate: Date? = null

    // 保额币种
    var siCurrencyCode: String? = null

    // 保额
    var sumInsured: String? = null

    // 保费币种
    var premiumCurrencyCode: String? = null

    // 应收保费/真实保费
    var duePremium: BigDecimal? = null

    // 总折扣率
    var totalDiscountRate: BigDecimal? = null

    // 短期费率方式
    var shortRateType: String? = null

    // 短期费率
    var shortRate: BigDecimal? = null

    // 共保标记
    var coInsuranceType: String? = null

    // 联保标记
    var internalCoInsuranceType: String? = null

    // 是否发送电子保单标志
    var isSendEPolicy: String? = null

    // 电子保单下载标志
    var isDownloadEPolicy: String? = null

    // 是否发送短信
    var isSendSms: String? = null

    // 司法管辖
    var judicalScopeCode: String? = null

    // 单证流水号
    var printNo: String? = null

    // 主险保费
    var sumMainPrem: BigDecimal? = null

    // 附加险保费
    var sumSubPrem: BigDecimal? = null

    // 总折扣金额
    var sumDisCount: BigDecimal? = null

    // 保单号码
    var policyNo: String? = null

    // 业务风险分类
    var belongToHandler2Code: String? = null

    // 业务风险分类名称
    var belongToHandler2Name: String? = null

    // 渠道信息
    var channelOpInfoList: List<ChannelOpInfo>? = null

    // 投保人信息
    var policyCustomerList: List<PolicyCustomer>? = null

    // 标的信息
    var policyLobList: List<PolicyLob>? = null

    // 农业信息
    var agriSubsidyList: List<AgriSubsidy>? = null

    // 发票信息
    var invoiceInfoList: List<InvoiceInfo>? = null

    // 付款信息
    var policyPaymentInfoList: List<PolicyPaymentInfo>? = null

    // 联保信息
    var innerCoInsuranceInfoList: List<InnerCoInsuranceInfo>? = null

    // 共保信息
    var coInsuranceInfoList: List<CoInsuranceInfo>? = null

    // 单证信息列表
    var visaInfoList: List<VisaInfo>? = null

    // 佣金列表
    var policyCommissionRateList: List<PolicyCommissionRate>? = null
}