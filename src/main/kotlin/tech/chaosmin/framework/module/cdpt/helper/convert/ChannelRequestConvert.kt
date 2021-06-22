package tech.chaosmin.framework.module.cdpt.helper.convert

import tech.chaosmin.framework.base.enums.GenderEnum
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner.*
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.DDReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.DDRequestHead
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDCReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDUReq
import java.util.*

/**
 * 证件类型码值 <p>
 * 111	居民身份证
 * 113	户口簿
 * 114	中国人民解放军军官证
 * 414	普通护照
 * 112	临时居民身份证
 * 123	警官证
 * 133	学生证
 * 211	离休证
 * 335	机动车驾驶证
 * 418	外国人旅行证
 * 511	台湾居民来往大陆通行证
 * 513	往来港澳通行证
 * 515	前往港澳通行证
 * 517	大陆居民往来台湾通行证
 * 554	外国人居留证
 * 990	其他个人有效证件
 * 991	未知个人证件
 * 801	组织机构代码证
 * 802	税务登记证
 * 803	营业执照
 * 810	统一社会信用代码
 * 811	社保保险登记证
 * 812	办学许可证
 * 993	其他企业有效证件
 * 994	未知企业证件
 * 28	港澳台居民居住证
 * 117	出生医学证明
 *
 * @author Romani min
 * @since 2021/6/21 19:04
 */
object ChannelRequestConvert {
    fun convert2DDCReq(policy: PolicyEntity): DDReq<DDCReq> {
        val request = DDReq<DDCReq>()
        request.requestHead = DDRequestHead(policy.orderNo!!)
        request.requestBody = DDCReq().apply {
            this.businessAttribute = "E00149"
            this.businessType = "1"
            this.policyNature = if ((policy.insuredList?.size ?: 0) < 3) "01" else "02"
            // 旅意险定额产品不需要传保费保额
            // this.duePremium = policy.totalPremium?.toBigDecimal()
            // this.sumInsured = BigDecimal(policy.totalSa!!).toString()
            this.effectiveDate = policy.effectiveTime
            this.expiryDate = policy.expiryTime
            this.productCode = policy.productCode
            this.proposalDate = Date()
            this.channelOpInfoList = Collections.singletonList(ChannelOpInfo().apply {
                this.channelProductCode = policy.productCode
                this.channelSeqNo = policy.orderNo
            })
            this.policyCustomerList = Collections.singletonList(PolicyCustomer().apply {
                this.customerName = policy.holder?.name
                this.partyCategory = "02"
                this.customerRoleCode = "1"
                this.idNo = policy.holder?.certiNo
                this.idType = "803"
            })
            this.policyLobList = Collections.singletonList(PolicyLob().apply {
                this.policyRiskList = Collections.singletonList(PolicyRisk().apply {
                    this.insuredGroupList = Collections.singletonList(InsuredGroup().apply {
                        this.insuredCount = policy.insuredList?.size?.toLong()
                        this.insuredGroupNo = 1L
                        this.numberOfCopies = 1
                        this.planCode = policy.productPlanCode
                    })
                    this.personInsuredList = policy.insuredList?.mapIndexed { index, i ->
                        PersonInsured().apply {
                            this.sequenceNumber = index.toLong() + 1
                            this.customerName = i.name
                            this.customerRoleCode = "2"
                            this.idNo = i.certiNo
                            this.dateOfBirth = i.birthday
                            this.idType = "111"
                            this.indiMobile = i.phoneNo
                            this.indiGenderCode = if (GenderEnum.MALE == i.gender!!) "1" else "2"
                            this.polHolderInsuredRelaCode = "80"
                        }
                    }
                })
            })
        }
        return request
    }

    fun convert2DDUReq(policy: PolicyEntity): DDReq<DDUReq> {
        val request = DDReq<DDUReq>()
        request.requestHead = DDRequestHead(policy.orderNo!!)
        request.requestBody = DDUReq().apply {
            this.policyDTO = Policy().apply {
                this.businessNo = policy.proposalNo
            }
            this.channelOpInfoDTO = ChannelOpInfo()
        }
        return request
    }
}