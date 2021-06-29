package tech.chaosmin.framework.module.cdpt.service.external

import org.assertj.core.api.Assertions
import org.assertj.core.util.DateUtil
import org.junit.jupiter.api.Test
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.base.enums.PolicyProcessEnum
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner.*
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.DDReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.DDRequestHead
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDCPReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDCReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDUReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.DDResp
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj.DDCResp
import tech.chaosmin.framework.module.cdpt.service.external.impl.DadiChannelRequestService
import tech.chaosmin.framework.utils.JsonUtil
import java.math.BigDecimal
import java.util.*
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/6/17 21:44
 */
class DadiChannelRequestServiceBootTest : BaseTestMain() {
    @Resource
    lateinit var dadiChannelRequestService: DadiChannelRequestService

    @Test
    fun test_calculatePremium() {
        val request = DDReq<DDCReq>()
        request.requestHead = DDRequestHead("blorder0013")
        request.requestBody = DDCReq().apply {
            this.businessAttribute = "E00149"
            this.businessType = "1"
            this.policyNature = "02"
            this.channelOpInfoList = Collections.singletonList(ChannelOpInfo().apply {
                this.channelProductCode = "EID"
                this.channelSeqNo = "2021061810015"
            })
            this.duePremium = BigDecimal("75").multiply(BigDecimal("3"))
            this.effectiveDate = DateUtil.parseDatetime("2021-07-01T00:00:00")
            this.expiryDate = DateUtil.parseDatetime("2021-07-30T23:59:59")
            this.policyCustomerList = Collections.singletonList(PolicyCustomer().apply {
                this.customerName = "堡垒科技"
                this.partyCategory = "02"
                this.customerRoleCode = "1"
                this.idNo = "340501198008040073"
                this.idType = "803"
                // this.indiGenderCode = "1"
                // this.indiDateOfBirth = DateUtil.parse("1980-08-04")
                // this.indiMobile = "19926542735"
            })
            this.policyLobList = Collections.singletonList(PolicyLob().apply {
                this.policyRiskList = Collections.singletonList(PolicyRisk().apply {
                    this.insuredGroupList = Collections.singletonList(InsuredGroup().apply {
                        this.insuredCount = 3L
                        this.insuredGroupNo = 1L
                        this.numberOfCopies = 1
                        this.planCode = "EGC2150028"
                    })
                    this.personInsuredList = listOf(PersonInsured().apply {
                        this.sequenceNumber = 1
                        this.customerName = "Romani"
                        this.customerRoleCode = "2"
                        this.idNo = "310225199410292416"
                        this.idType = "111"
                        this.indiGenderCode = "1"
                        this.keyCoveragePlanGroup = "1"
                        this.partyCategory = "01"
                        this.polHolderInsuredRelaCode = "1"
                        this.socialSecurityFlag = "Y"
                    }, PersonInsured().apply {
                        this.sequenceNumber = 2
                        this.customerName = "Akiman"
                        this.customerRoleCode = "2"
                        this.idNo = "519004195309170021"
                        this.idType = "111"
                        this.indiGenderCode = "1"
                        this.keyCoveragePlanGroup = "1"
                        this.partyCategory = "01"
                        this.polHolderInsuredRelaCode = "1"
                        this.socialSecurityFlag = "Y"
                    }, PersonInsured().apply {
                        this.sequenceNumber = 3
                        this.customerName = "Riqiu"
                        this.customerRoleCode = "2"
                        this.idNo = "519004195309170021"
                        this.idType = "111"
                        this.indiGenderCode = "1"
                        this.keyCoveragePlanGroup = "1"
                        this.partyCategory = "01"
                        this.polHolderInsuredRelaCode = "1"
                        this.socialSecurityFlag = "Y"
                    })
                })
            })
            this.productCode = "EGC"
            this.proposalDate = Date()
            this.sumInsured = BigDecimal("1607800").multiply(BigDecimal("3")).toString()
        }
        val result = dadiChannelRequestService.request(PolicyProcessEnum.PREMIUM_TRIAL, request) {
            JsonUtil.decode(it, DDResp::class.java, DDCResp::class.java)
        }
        println(JsonUtil.encode(result, true))
        Assertions.assertThat(result).isNotNull
    }

    @Test
    fun test_underwriting() {
        val request = DDReq<DDUReq>()
        request.requestHead = DDRequestHead("blorder0015")
        request.requestBody = DDUReq().apply {
            this.policyDTO = Policy().apply {
                this.businessNo = "TEGC21310116020000000001"
            }
            this.channelOpInfoDTO = ChannelOpInfo()
        }
        val result = dadiChannelRequestService.request(PolicyProcessEnum.UNDERWRITING, request) {
            JsonUtil.decode(it, DDResp::class.java, DDCResp::class.java)
        }
        println(JsonUtil.encode(result, true))
        Assertions.assertThat(result).isNotNull
    }

    @Test
    fun test_cancelPolicy() {
        val request = DDReq<DDCPReq>()
        request.requestHead = DDRequestHead("test-order-no-0102")
        request.requestBody = DDCPReq().apply {
            this.endorseMainInfo = EndorseMainInfo().apply {
                this.policyNo = "PEID21310116020000000002"
                this.applyDate = cn.hutool.core.date.DateUtil.format(Date(), "yyyy-MM-dd HH:mm:ss")
            }
            this.channelOpInfo = ChannelOpInfo()
        }
        val result = dadiChannelRequestService.request(PolicyProcessEnum.POLICY_CANCEL, request) {
            JsonUtil.decode(it, DDResp::class.java, DDCResp::class.java)
        }
        println(JsonUtil.encode(result, true))
        Assertions.assertThat(result).isNotNull
    }
}