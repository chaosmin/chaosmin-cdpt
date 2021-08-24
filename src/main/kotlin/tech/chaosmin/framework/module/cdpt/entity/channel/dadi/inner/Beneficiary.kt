package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.util.*

/**
 * 大地保险受益人信息 <p>
 *
 * @author Romani min
 * @since 2021/6/17 18:55
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Beneficiary {
    // 序号
    var sequenceNumber: Long? = null

    // 客户性质
    var partyCategory: String? = null

    // 受益人代码
    var customerNo: String? = null

    // 与被保险人关系
    var benefitInsuredRelaCode: String? = null

    // 客户姓名/受益人姓名
    var customerName: String? = null

    // 受益人年龄
    var age: Long? = null

    // 性别
    var genderCode: String? = null

    // 出生日期
    var dateOfBirth: Date? = null

    // 联系地址
    var address: String? = null

    // 邮件地址
    var email: String? = null

    // 证件类别
    var idType: String? = null

    // 证件号
    var idNo: String? = null

    // 证件有效期
    var idValidThru: Date? = null

    // 国籍
    var nationalityCode: String? = null

    // 所在国家
    var locationCountryCode: String? = null

    // 联系电话, 固定电话
    var tel: String? = null

    // 关联被保险人序号
    var refInsuredSeq: Long? = null

    // 受益方式
    var benefitModeCode: String? = null

    // 受益顺序
    var benefitSeqCode: String? = null

    // 受益份额
    var benefitRate: BigDecimal? = null

    // 代理人身份
    var authAgentIdentity: String? = null

    // 单位性质
    var branchNatureCode: String? = null
}