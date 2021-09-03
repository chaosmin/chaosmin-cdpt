package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*

/**
 * 大地保险投保人信息 <p>
 *
 * @author Romani min
 * @since 2021/6/17 11:24
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class PolicyCustomer {
    // 客户角色类型(投保人,关键联系人)
    var customerRoleCode: String? = null

    // 上海平台客户编码
    var platCustomSeqNo: String? = null

    // 是否与投保人为同一人
    var isContactPolHolder: String? = null

    // 与被保险人关系
    var polHolderInsuredRelaCode: String? = null

    // 常驻地址
    var permanentAddress: String? = null

    // 客户类型
    var partyCategory: String? = null

    // 客户姓名/名称
    var customerName: String? = null

    // 年龄
    var indiAge: Long? = null

    // 性别
    var indiGenderCode: String? = null

    // 出生日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    var indiDateOfBirth: Date? = null

    // 证件类型
    var idType: String? = null

    // 证件号码
    var idNo: String? = null

    // 证件有效期
    var idValidThru: Date? = null

    // 个人-手机号
    var indiMobile: String? = null

    // 单位-手机号
    var orgMobile: String? = null

    // 邮箱地址
    var email: String? = null

    // 个人-职业代码
    var indiOccupationCode: String? = null

    // 个人-职业类别
    var indiOccupationType: String? = null

    // 国籍
    var nationalityCode: String? = null

    // 所在国家
    var locationCountryCode: String? = null

    // 联系人电话
    var contactPersonTel: String? = null

    // 联系地址
    var address: String? = null

    // 邮编
    var postCode: String? = null

    // 单位性质(反洗钱专用)
    var antiMoneyOfUnitNatureCode: String? = null

    // 单位性质
    var branchNatureCode: String? = null

    // 行业分类(存放代码表中行业小类码值)
    var nationalEconomyCate: String? = null

    // 机构-行业二级分类(存放代码表中行业小类码值)
    var industryCategory: String? = null

    // 社保保险登记号
    var orgSocialSecurityRegNo: String? = null

    // 营业执照有效期
    var businessLicenseValidThru: Date? = null
}