package tech.chaosmin.framework.module.cdpt.logic.outer.partner

import org.junit.jupiter.api.Test
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.base.enums.PolicyProcessEnum
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.DDReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDCReq
import tech.chaosmin.framework.utils.JsonUtil
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/9/6 11:03
 */
internal class DadiInsurerServiceTest : BaseTestMain() {
    @Resource
    lateinit var dadiInsurerService: DadiInsurerService

    @Test
    fun header() {
        val str =
            """{"requestHead":{"seqNo":"202107091322060003","requestId":"a6aa0ab4-6b4a-4085-9026-b778201fdc82","requestTime":"2021-07-09T13:22:47","version":null},"requestBody":{"proposalNo":null,"historyPolicyNo":null,"ifAutoRenewal":null,"businessAttribute":"E00149","policyNature":"01","insuredListType":null,"productCode":"EGC","businessType":"1","orgCode":null,"issueOrgCode":null,"issueUserCode":null,"issueAddress":null,"businessSourceCode":null,"businessSource2Code":null,"repairChannelCode":null,"effectiveDate":"2021-07-10 00:00:00","expiryDate":"2021-07-10 23:59:59","proposalDate":"2021-07-09 13:22:47","regularSettlementModeCode":null,"regularSettlementDate":null,"latestRegularSettleDate":null,"siCurrencyCode":null,"sumInsured":null,"premiumCurrencyCode":null,"duePremium":null,"totalDiscountRate":null,"shortRateType":null,"shortRate":null,"coInsuranceType":null,"internalCoInsuranceType":null,"judicalScopeCode":null,"printNo":null,"sumMainPrem":null,"sumSubPrem":null,"sumDisCount":null,"policyNo":null,"belongToHandler2Code":null,"belongToHandler2Name":null,"channelOpInfoList":[{"channelCode":"E00149-002","channelComCode":"31011500","channelProductCode":"EGC","channelSeqNo":"202107091322060003"}],"policyCustomerList":[{"customerRoleCode":"1","partyCategory":"02","customerName":"堡垒","idType":"803","idNo":"123456"}],"policyLobList":[{"policyRiskList":[{"insuredGroupList":[{"insuredGroupNo":1,"planCode":"EGC2150028","numberOfCopies":1,"insuredCount":1}],"personInsuredList":[{"sequenceNumber":1,"customerRoleCode":"2","polHolderInsuredRelaCode":"80","customerName":"张三","indiGenderCode":"1","dateOfBirth":"1980-06-12","idType":"414","idNo":"ETJW01","indiMobile":""}]}]}],"agriSubsidyList":null,"invoiceInfoList":null,"policyPaymentInfoList":null,"innerCoInsuranceInfoList":null,"coInsuranceInfoList":null,"visaInfoList":null,"policyCommissionRateList":null}}"""
        val req = JsonUtil.decode(str, DDReq::class.java, DDCReq::class.java)

        val header = dadiInsurerService.header(PolicyProcessEnum.UNDERWRITING, req!!)
        println(header)

        val old = dadiInsurerService.old(req)
        println(old)
    }
}