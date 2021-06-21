package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner.*
import java.math.BigDecimal
import java.util.*

/**
 * 大地保险保费试算响应体 <p>
 *
 * @author Romani min
 * @since 2021/6/17 21:26
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class DDCResp {
    // [HAS]
    var isEProposal: String? = null

    // [HAS] 保单性质(团单标记, 区分个团)
    var policyNature: String? = null

    // [HAS]
    var beforeVatPremium: BigDecimal? = null

    // [HAS] 司法管辖
    var judicalScopeCode: String? = null

    // [HAS] 是否打包产品
    var isPackageProduct: String? = null

    // [HAS] 投保单号
    var proposalNo: String? = null

    // 原保单号
    var historyPolicyNo: String? = null

    // [HAS] 业务来源属性
    var businessAttribute: String? = null

    // 分户类型(被保险人清单类型)
    var insuredListType: String? = null

    // 产品代码
    var productCode: String? = null

    // 请求业务类型
    var businessType: String? = null

    // [HAS] 是否自动核保
    var isAutoUw: String? = null

    var sequenceNumber: Int? = null

    // 核保通过时间
    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    var underwritingDate: Date? = null

    // 归属机构代码
    var orgCode: String? = null

    // 出单机构代码
    var issueOrgCode: String? = null

    // [HAS] 出单员代码
    var issueUserCode: String? = null

    // 出单点地址
    var issueAddress: String? = null

    // [HAS] 业务来源1
    var businessSourceCode: String? = null

    // 业务来源2
    var businessSource2Code: String? = null

    //  业务编码(RepairChannelCode)/Q码
    var repairChannelCode: String? = null

    // 起保日期/起始生效日期, 支持时分秒
    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    var effectiveDate: Date? = null

    // 终保日期/终止失效日期, 支持时分秒
    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    var expiryDate: Date? = null

    // 投保日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    var proposalDate: Date? = null

    // 定期结算方式
    var regularSettlementModeCode: String? = null

    // 是否定期结算标识
    var isRegularSettlement: String? = null

    // 定期结算日期
    var regularSettlementDate: Date? = null

    // 定期结算最晚结算日期
    var latestRegularSettleDate: Date? = null

    // [HAS] 保额币种
    var siCurrencyCode: String? = null

    // [HAS] 保额
    var sumInsured: BigDecimal? = null

    // 保费币种
    var premiumCurrencyCode: String? = null

    // 应收保费/真实保费
    var duePremium: BigDecimal? = null

    // [HAS] 总折扣率
    var totalDiscountRate: BigDecimal? = null

    // [HAS]
    var groupBusinessCode: String? = null

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

    // 单证流水号
    var printNo: String? = null

    // [HAS] 主险保费
    var sumMainPrem: BigDecimal? = null

    // 附加险保费
    var sumSubPrem: BigDecimal? = null

    // 总折扣金额
    var sumDisCount: BigDecimal? = null

    // 保单号码
    var policyNo: String? = null

    // 业务风险分类
    var belongToHandler2Code: String? = null

    // [HAS] 业务风险分类名称
    var belongToHandler2Name: String? = null

    // 产品二级险类
    var secondLine: String? = null

    // 代理人姓名
    var agentName: String? = null

    // [HAS] 归属机构名称
    var orgName: String? = null

    // [HAS] 产品名称
    var productName: String? = null

    // 出单机构名称
    var issueOrgName: String? = null

    // [HAS] 出单人员名称
    var issueUserName: String? = null

    // 销售单位
    var salesName: String? = null

    // 归属机构地址
    var comAddress: String? = null

    // [HAS] 归属机构邮编
    var comPostCode: String? = null

    // 操作日期
    var operateDate: String? = null

    // 中文保额
    var amountC: String? = null

    // 中文保费
    var premiumC: String? = null

    // 保单状态
    var proposalStatus: String? = null

    // 保单状态
    var policyStatus: String? = null

    // 渠道信息
    var channelOpInfoList: List<ChannelOpInfo>? = null
    var policyCustomerList: List<PolicyCustomer>? = null
    var policyLobList: List<PolicyLob>? = null
    var agriSubsidyList: List<AgriSubsidy>? = null
    var invoiceInfoList: List<InvoiceInfo>? = null
    var policyPaymentInfoList: List<PolicyPaymentInfo>? = null
    var innerCoInsuranceInfoList: List<InnerCoInsuranceInfo>? = null
    var coInsuranceInfoList: List<CoInsuranceInfo>? = null
    var visaInfoList: List<VisaInfo>? = null
    var policyCommissionRateList: List<PolicyCommissionRate>? = null
}