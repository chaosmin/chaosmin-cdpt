package tech.chaosmin.framework.module.cdpt.helper.convert

import cn.hutool.core.date.DateUtil
import cn.hutool.extra.spring.SpringUtil
import tech.chaosmin.framework.base.enums.CertiTypeEnum
import tech.chaosmin.framework.base.enums.GenderEnum
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner.*
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.DDReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.DDRequestHead
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDCPReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDCReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDUReq
import tech.chaosmin.framework.module.mgmt.service.ResDataService
import java.util.*

/**
 * @author Romani min
 * @since 2021/6/21 19:04
 */
object ChannelRequestConvert {
    fun convert2DDCReq(policy: PolicyEntity): DDReq<DDCReq> {
        val resData = SpringUtil.getBean(ResDataService::class.java)
        val request = DDReq<DDCReq>()
        request.requestHead = DDRequestHead(policy.orderNo!!)
        request.requestBody = DDCReq().apply {
            this.businessAttribute = "E00149"
            this.businessType = "1"
            this.policyNature = if ((policy.insuredList?.size ?: 0) == 1) "01" else "02"
            this.effectiveDate = policy.effectiveTime
            this.expiryDate = policy.expiryTime
            this.productCode = policy.goodsPlan?.productCode
            this.proposalDate = Date()
            this.channelOpInfoList = Collections.singletonList(ChannelOpInfo().apply {
                this.channelProductCode = policy.goodsPlan?.productCode
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
                        this.planCode = policy.goodsPlan?.productPlanCode
                    })
                    this.personInsuredList = policy.insuredList?.mapIndexed { index, i ->
                        PersonInsured().apply {
                            this.sequenceNumber = index.toLong() + 1
                            this.customerName = i.name
                            this.customerRoleCode = "2"
                            this.idNo = i.certiNo
                            this.dateOfBirth = i.birthday
                            this.idType = resData.getValue("dadi", CertiTypeEnum::class.simpleName!!, i.certiType?.getCode().toString())
                            this.indiMobile = i.phoneNo
                            this.indiGenderCode = resData.getValue("dadi", GenderEnum::class.simpleName!!, i.gender?.getCode().toString())
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

    fun convert2DDCPReq(policy: PolicyEntity): DDReq<DDCPReq> {
        val request = DDReq<DDCPReq>()
        request.requestHead = DDRequestHead(policy.orderNo!!)
        request.requestBody = DDCPReq().apply {
            this.endorseMainInfo = EndorseMainInfo().apply {
                this.policyNo = policy.policyNo
                this.applyDate = DateUtil.format(Date(), "yyyy-MM-dd HH:mm:ss")
            }
            this.channelOpInfo = ChannelOpInfo()
        }
        return request
    }
}