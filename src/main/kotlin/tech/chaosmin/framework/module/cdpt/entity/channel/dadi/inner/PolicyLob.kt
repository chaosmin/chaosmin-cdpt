package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import java.math.BigDecimal
import java.util.*

/**
 * 大地保险标的信息 <p>
 * @author Romani min
 * @since 2021/6/17 11:25
 */
class PolicyLob {
    // 附属被保险人人数,仅团险使用
    var addInsuredCount: Long? = null

    // 总被保险人人数,仅团险使用
    var totalInsuredCount: Long? = null

    // 贷款币别
    var goodsPaymentCurrency: String? = null

    // 贷款类型
    var loanType: String? = null

    // 贷款合同号
    var contractNo: String? = null

    // 贷款银行代码
    var loanBankCode: String? = null

    // 贷款起期
    var loanPeriodStart: Date? = null

    // 贷款止期
    var loanPeriodEnd: Date? = null

    // 保额确定方式
    var sumInsuredMode: String? = null

    // 借款金额
    var loanAmount: BigDecimal? = null

    // 学校类别
    var schoolType: String? = null

    // 学校性质
    var schoolNature: String? = null

    // 健康专项业务类别
    var healthSpecialBusinessType: String? = null

    // 参保人群类型
    var insuredCrowdType: String? = null

    // 缴费方式
    var ahPaymentMethodCode: Long? = null

    // 续保缴费方式
    var renewPaymentMethodCode: String? = null

    // 地区信息列表
    var policyRegionList: List<PolicyRegion>? = null

    // 公共保障分组列表
    var sharedCoverageGroupList: List<SharedCoverageGroup>? = null

    // 保单或者产品层面的特别约定列表(formCategory为2)
    var policyFormList: List<PolicyForm>? = null

    // 标的信息列表
    var policyRiskList: List<PersonInsured>? = null

    // 工程类信息列表
    var engineeringList: List<Engineering>? = null

    // 工程类地址信息列表
    var addressDetailList: List<AddressDetail>? = null

    // 司乘信息列表
    var vehicleList: List<Vehicle>? = null

    // 旅游信息列表
    var tourGroupList: List<TourGroup>? = null
}