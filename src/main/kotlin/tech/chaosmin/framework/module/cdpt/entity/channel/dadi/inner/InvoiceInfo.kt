package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * 大地保险发票信息 <p>
 *
 * @author Romani min
 * @since 2021/6/17 18:40
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class InvoiceInfo {
    // 发票类型
    var invoiceType: String? = null

    // 客户类型
    var customerType: String? = null

    // 开票公司信息(同投保人/同被保人/第三方)
    var copyDataFromType: String? = null

    // 银行代码(总行)/开户行代码(总行)
    var bankCode: String? = null

    // 开户行名称(直接存储支行名称,原始数据量较大,无码表。不能使用开户行代码)
    var depositeBankName: String? = null

    // 开户行账号
    var accountNo: String? = null

    // 公司名称
    var companyName: String? = null

    // 纳税人类型
    var taxPayerType: String? = null

    // 纳税人识别号
    var taxPayerNo: String? = null

    // 税务登记地址
    var taxRegisterAddress: String? = null

    // 税务登记电话
    var taxRegisterTel: String? = null

    // 邮箱地址
    var email: String? = null

    // 手机号码
    var mobile: String? = null
}