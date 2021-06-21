package tech.chaosmin.framework.module.cdpt.service.external

import org.assertj.core.api.Assertions
import org.assertj.core.util.DateUtil
import org.junit.jupiter.api.Test
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner.*
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.DDReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.DDRequestHead
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDCPReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDCReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDUReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.DDResp
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj.DDCResp
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj.DDUResp
import tech.chaosmin.framework.utils.JsonUtil
import java.math.BigDecimal
import java.util.*
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/6/17 21:44
 */
class DadiPolicyServiceBootTest : BaseTestMain() {
    @Resource
    lateinit var dadiPolicyService: DadiPolicyService

    private val MOCK_CALCULATE_PREMIUM_RESPONSE = """
        {
        	"responseHead": {
        		"responseTime": "2021-06-18T14:16:38",
        		"resultCode": "0",
        		"resultMessage": "下单接口任务执行成功！",
        		"responseId": "bf72204c-c15c-47e9-97a0-6491426f8404"
        	},
        	"responseBody": {
        		"isEProposal": "Y",
        		"policyNature": "01",
        		"beforeVatPremium": 28.29,
        		"judicalScopeCode": "01",
        		"productName": "大地欢乐游意外伤害保险",
        		"isPackageProduct": "N",
        		"comPostCode": "200080",
        		"sumInsured": 548000,
        		"belongToHandler2Name": "王溃溃",
        		"issueUserCode": "8000579613",
        		"businessSourceCode": "0101",
        		"siCurrencyCode": "CNY",
        		"channelOpInfoList": [{
        			"channelComCode": "31011500",
        			"channelSeqNo": "2021061810001",
        			"channelProductCode": "EID",
        			"trxDate": "2021-06-18",
        			"channelCode": "E00149-002"
        		}],
        		"isAutoUw": "Y",
        		"sequenceNumber": 1,
        		"issueUserName": "钟思妮",
        		"orgName": "上海分公司营业部银保渠道部",
        		"totalDiscountRate": 0,
        		"groupBusinessCode": "02",
        		"sumMainPrem": 30,
        		"DataCheckResult": "Success",
        		"businessAttribute": "E00149",
        		"proposalNo": "TEID21310116020000000003",
        		"premiumBookExchangeRate": 1,
        		"salesName": "上海分公司营业部银保渠道部",
        		"proposalStatus": "262",
        		"shortRateType": "04",
        		"policyHolderName": "郑丹丹",
        		"operateDate": "2021-06-18",
        		"proposalDate": "2020-03-20",
        		"siLocalExchangeRate": 1,
        		"sumSubPrem": 0,
        		"electronicPolicyTemplateId": "form032_1_N",
        		"duePremium": 30,
        		"secondLine": 72000,
        		"internalCoInsuranceType": "01",
        		"policyCustomerList": [{
        			"indiAge": 40,
        			"indiDateOfBirth": "1980-08-04",
        			"customerType": "01",
        			"nationalityCode": "CHN",
        			"partyCategory": "01",
        			"idType": "111",
        			"indiMobile": "19926542735",
        			"customerRoleCode": "1",
        			"idNo": "340501198008040073",
        			"indiGenderCode": "1",
        			"customerName": "郑丹丹",
        			"email": "qinyun12@qq.com"
        		}],
        		"coInsuranceType": "01",
        		"issueOrgCode": "31011602",
        		"policyStatus": 1,
        		"isSpecialBusiness": "N",
        		"policyLobList": [{
        			"totalInsuredCount": 1,
        			"policyRiskList": [{
        				"personInsuredList": [{
        					"sequenceNumber": 1,
        					"socialSecurityFlag": "Y",
        					"benefitModeCode": "1",
        					"idType": "111",
        					"duePremium": 30,
        					"dateOfBirth": "1953-09-17",
        					"isOnJob": "N",
        					"idNo": "519004195309170021",
        					"customerName": "吴银月B",
        					"keyCoveragePlanGroup": "1",
        					"nationalityCode": "CHN",
        					"partyCategory": "01",
        					"polHolderInsuredRelaCode": "1",
        					"customerRoleCode": "2",
        					"numberOfCopies": 1,
        					"hasAffilInsured": "N",
        					"indiGenderCode": "1",
        					"age": 66
        				}],
        				"insuredGroupList": [{
        					"insuredGroupNo": 1,
        					"duePremium": 30,
        					"policyCoverageList": [{
        						"clauseCode": "CF1100289",
        						"sequenceNumber": 1,
        						"shortRate": 1,
        						"kindCode": "C100448",
        						"vatRateType": "A",
        						"vatRate": 0.06,
        						"duePremium": 0.5,
        						"premiumRate": 0.0001,
        						"policyStatus": 2,
        						"isNonDeductible": "N",
        						"sumInsured": 5000,
        						"avgSumInsured": 5000,
        						"isFinalLevelCt": "Y",
        						"unitPremium": 0.5,
        						"clauseName": "大地欢乐游意外伤害保险条款"
        					}, {
        						"clauseCode": "CF1100289",
        						"eachDeductible": 0,
        						"kindCode": "C100444",
        						"vatRateType": "A",
        						"vatRate": 0.06,
        						"duePremium": 2,
        						"payRatioLimit": 1,
        						"premiumRate": 0.0001,
        						"policyStatus": 2,
        						"sumInsured": 20000,
        						"isFinalLevelCt": "Y",
        						"limitDeductibleList": [{
        							"sequenceNumber": 1,
        							"isCustomLimitDeductible": "N",
        							"limitDeductibleValue": 1,
        							"limitDeductibleType": "2",
        							"limitDeductibleCode": "L100084",
        							"limitDeductibleLevel": "4",
        							"limitDeductibleName": "赔偿/给付比例",
        							"limitDeductibleUnit": "9"
        						}, {
        							"sequenceNumber": 2,
        							"isCustomLimitDeductible": "N",
        							"limitDeductibleValue": 0,
        							"limitDeductibleType": "1",
        							"limitDeductibleCode": "L200018",
        							"limitDeductibleLevel": "4",
        							"limitDeductibleName": "次免赔额",
        							"limitDeductibleUnit": "14"
        						}],
        						"unitPremium": 2,
        						"clauseName": "大地欢乐游意外伤害保险条款",
        						"sequenceNumber": 2,
        						"shortRate": 1,
        						"isNonDeductible": "N",
        						"avgSumInsured": 20000
        					}, {
        						"clauseCode": "CF1100289",
        						"eachDeductible": 0,
        						"kindCode": "C100445",
        						"vatRateType": "A",
        						"vatRate": 0.06,
        						"duePremium": 1.8,
        						"payRatioLimit": 1,
        						"premiumRate": 0.00009,
        						"policyStatus": 2,
        						"sumInsured": 20000,
        						"isFinalLevelCt": "Y",
        						"limitDeductibleList": [{
        							"sequenceNumber": 1,
        							"isCustomLimitDeductible": "N",
        							"limitDeductibleValue": 1,
        							"limitDeductibleType": "2",
        							"limitDeductibleCode": "L100084",
        							"limitDeductibleLevel": "4",
        							"limitDeductibleName": "赔偿/给付比例",
        							"limitDeductibleUnit": "9"
        						}, {
        							"sequenceNumber": 2,
        							"isCustomLimitDeductible": "N",
        							"limitDeductibleValue": 0,
        							"limitDeductibleType": "1",
        							"limitDeductibleCode": "L200018",
        							"limitDeductibleLevel": "4",
        							"limitDeductibleName": "次免赔额",
        							"limitDeductibleUnit": "14"
        						}],
        						"unitPremium": 1.8,
        						"clauseName": "大地欢乐游意外伤害保险条款",
        						"sequenceNumber": 3,
        						"shortRate": 1,
        						"isNonDeductible": "N",
        						"avgSumInsured": 20000
        					}, {
        						"clauseCode": "CF1100289",
        						"kindCode": "C100446",
        						"vatRateType": "A",
        						"vatRate": 0.06,
        						"duePremium": 1,
        						"premiumRate": 0.00066666667,
        						"policyStatus": 2,
        						"sumInsured": 1500,
        						"isFinalLevelCt": "Y",
        						"unitPremium": 1,
        						"clauseName": "大地欢乐游意外伤害保险条款",
        						"sequenceNumber": 4,
        						"shortRate": 1,
        						"subsidyAmount": "50",
        						"isNonDeductible": "N",
        						"avgSumInsured": 1500,
        						"subsidyDay": "30"
        					}, {
        						"clauseCode": "CF1100289",
        						"sequenceNumber": 5,
        						"shortRate": 1,
        						"kindCode": "C100443",
        						"vatRateType": "A",
        						"vatRate": 0.06,
        						"duePremium": 1,
        						"premiumRate": 0.00002,
        						"policyStatus": 2,
        						"isNonDeductible": "N",
        						"sumInsured": 50000,
        						"avgSumInsured": 50000,
        						"isFinalLevelCt": "Y",
        						"unitPremium": 1,
        						"clauseName": "大地欢乐游意外伤害保险条款"
        					}, {
        						"clauseCode": "CF1100289",
        						"sequenceNumber": 6,
        						"shortRate": 1,
        						"kindCode": "C100442",
        						"vatRateType": "A",
        						"vatRate": 0.06,
        						"duePremium": 17.9,
        						"premiumRate": 0.000179,
        						"policyStatus": 2,
        						"isNonDeductible": "N",
        						"sumInsured": 100000,
        						"disabilityStandard": "3",
        						"avgSumInsured": 100000,
        						"isFinalLevelCt": "Y",
        						"unitPremium": 17.9,
        						"clauseName": "大地欢乐游意外伤害保险条款"
        					}, {
        						"clauseCode": "CF1100289",
        						"kindCode": "C100447",
        						"vatRateType": "A",
        						"vatRate": 0.06,
        						"duePremium": 0.8,
        						"premiumRate": 0.00053333333,
        						"policyStatus": 2,
        						"sumInsured": 1500,
        						"isFinalLevelCt": "Y",
        						"limitDeductibleList": [{
        							"sequenceNumber": 1,
        							"isCustomLimitDeductible": "N",
        							"limitDeductibleValue": 30,
        							"limitDeductibleType": "2",
        							"limitDeductibleCode": "L100152",
        							"limitDeductibleLevel": "4",
        							"limitDeductibleName": "最多赔偿/给付天数",
        							"limitDeductibleUnit": "7"
        						}],
        						"unitPremium": 0.8,
        						"clauseName": "大地欢乐游意外伤害保险条款",
        						"sequenceNumber": 7,
        						"shortRate": 1,
        						"maxPayDay": 30,
        						"subsidyAmount": "50",
        						"isNonDeductible": "N",
        						"avgSumInsured": 1500,
        						"subsidyDay": "30"
        					}, {
        						"clauseCode": "CF1100290",
        						"sequenceNumber": 1,
        						"shortRate": 1,
        						"kindCode": "C100469",
        						"vatRateType": "A",
        						"vatRate": 0.06,
        						"duePremium": 1,
        						"premiumRate": 0.00001,
        						"policyStatus": 2,
        						"isNonDeductible": "N",
        						"sumInsured": 100000,
        						"disabilityStandard": "3",
        						"avgSumInsured": 100000,
        						"isFinalLevelCt": "Y",
        						"unitPremium": 1,
        						"clauseName": "大地通达公共交通工具意外伤害保险条款"
        					}, {
        						"clauseCode": "CF1100290",
        						"sequenceNumber": 2,
        						"shortRate": 1,
        						"kindCode": "C100590",
        						"vatRateType": "A",
        						"vatRate": 0.06,
        						"duePremium": 2,
        						"premiumRate": 0.00004,
        						"policyStatus": 2,
        						"isNonDeductible": "N",
        						"sumInsured": 50000,
        						"disabilityStandard": "3",
        						"avgSumInsured": 50000,
        						"isFinalLevelCt": "Y",
        						"unitPremium": 2,
        						"clauseName": "大地通达公共交通工具意外伤害保险条款"
        					}, {
        						"clauseCode": "CF1100290",
        						"sequenceNumber": 3,
        						"shortRate": 1,
        						"kindCode": "C100468",
        						"vatRateType": "A",
        						"vatRate": 0.06,
        						"duePremium": 1,
        						"premiumRate": 0.00001,
        						"policyStatus": 2,
        						"isNonDeductible": "N",
        						"sumInsured": 100000,
        						"disabilityStandard": "3",
        						"avgSumInsured": 100000,
        						"isFinalLevelCt": "Y",
        						"unitPremium": 1,
        						"clauseName": "大地通达公共交通工具意外伤害保险条款"
        					}, {
        						"clauseCode": "CF1100290",
        						"sequenceNumber": 4,
        						"shortRate": 1,
        						"kindCode": "C100470",
        						"vatRateType": "A",
        						"vatRate": 0.06,
        						"duePremium": 1,
        						"premiumRate": 0.00001,
        						"policyStatus": 2,
        						"isNonDeductible": "N",
        						"sumInsured": 100000,
        						"disabilityStandard": "3",
        						"avgSumInsured": 100000,
        						"isFinalLevelCt": "Y",
        						"unitPremium": 1,
        						"clauseName": "大地通达公共交通工具意外伤害保险条款"
        					}],
        					"planName": "大地欢乐游意外伤害保险-乐游升级计划一",
        					"numberOfCopies": 1,
        					"policyFormList": [{
        						"customFormNo": "F2200377",
        						"sequenceNumber": 1,
        						"customFormTitle": "本特约条款扩展特殊风险包括：战争、恐怖主义、高风险活动或其他。",
        						"formCategory": "1",
        						"customFormDescription": "本特约条款扩展特殊风险包括：战争、恐怖主义、高风险活动或其他。"
        					}],
        					"planCode": "EID2150146",
        					"insuredCount": 1
        				}]
        			}],
        			"ahPaymentMethodCode": "02",
        			"policyFormList": [{
        				"customFormNo": "A00065",
        				"sequenceNumber": 1,
        				"customFormTitle": "自编特约",
        				"formCategory": "2",
        				"customFormContent": "1、在保险期间内，被保险人旅行期间遭受意外或者突发急性病，在二级以上（含）公立医院治疗由该意外引致的伤害或者该急性病，保险人就该意外或者该急性病发生之日起九十日所支出的符合当地城镇职工基本医疗保险规定的支/给付范围和标准的、医学必要的医疗费用（以下简称“每次事故合理医疗费用”），按“（每次事故合理医疗费用-人民币0元）×100%”给付意外医疗或者急性病医疗保险金。\r2、 公共交通工具意外伤害-汽车责任含旅游大巴。\r3、 本方案投保年龄为30天-85周岁。针对其中70周岁以上的被保险人，保险人承担的“意外身故及伤残”责任限额为5万、“突发急性病身故”的责任限额为2.5万元。\r4、本方案承保非竞技类高风险运动，因非竞技类高风险运动意外身故的保险金额为5万元，不与意外身故叠加赔付；因非竞技类高风险运动意外受伤的意外医疗保额为1,000元，无免赔额100%赔付。\r5、 本方案承保低于六千米以下高原地区，因高原反应突发急性病身故的保险金额为5万元；因高原反应突发急性病医疗的保险金额为2万元，无免赔额100%赔付。\r6、 未成年人赔偿限额按保监会规定。\r7、 本方案每人限投一份，多投无效。"
        			}, {
        				"customFormNo": "A00116",
        				"sequenceNumber": 2,
        				"customFormTitle": "伤残特约（行标）",
        				"formCategory": "2",
        				"customFormContent": "被保险人自遭受该意外之日起一百八十日内以该意外为直接、完全原因而导致《人身保险伤残评定标准及代码》（JR/T 0083—2013，保监发[2014]6号）中所列伤残的，保险人按该处残疾的伤残等级对应的给付比例和该被保险人的意外伤害保险金额的乘积给付意外伤残保险金。伤残等级对应的保险金给付比例分为十档，伤残程度第一级对应的保险金给付比例为100%，伤残程度第十级对应的保险金给付比例为10%，每级相差10%。"
        			}]
        		}],
        		"expiryDate": "2021-07-30T23:59:59",
        		"regionCode": "310000",
        		"isRenewable": "Y",
        		"isSendSms": "N",
        		"orgCode": "31011504",
        		"bookCurrency": "CNY",
        		"policyPaymentInfoList": [{
        			"sequenceNumber": 1,
        			"installmentPeriodCount": 1,
        			"installmentList": [{
        				"sequenceNumber": 1,
        				"installmentDate": "2021-07-01",
        				"installmentAmount": 30,
        				"dueDate": "2021-07-01",
        				"installmentPeriodSeq": 1,
        				"serialNo": 1
        			}]
        		}],
        		"premiumCurrencyCode": "CNY",
        		"shortRate": 1,
        		"businessSource2Code": "0101",
        		"invoiceInfoList": [{
        			"customerType": "1",
        			"taxPayerType": "4",
        			"companyName": "郑丹丹",
        			"invoiceType": "2",
        			"copyDataFromType": "1"
        		}],
        		"renewalType": "1",
        		"idCheckResultCode": "100",
        		"localCurrencyCode": "CNY",
        		"isSavePlanDetail": "N",
        		"issueOrgName": "浦东支公司综合部",
        		"comAddress": "上海市虹口区吴淞路130号704室",
        		"businessCate": "1",
        		"insuredListType": "1",
        		"productCode": "EID",
        		"cessionBasisCode": 2,
        		"policyType": "1",
        		"channelCodeByPerson": "C0000016",
        		"effectiveDate": "2021-07-01T00:00:00",
        		"belongToHandler2Code": "8000500301"
        	}
        }
    """.trimIndent()
    private val MOCK_UNDERWRITING_RESPONSE = """
        {
            "responseHead": {
            	"responseTime": "2021-06-18T14:26:16",
            	"resultCode": "0",
            	"resultMessage": "提交核保接口任务执行成功！",
            	"responseId": "3ed6c3e7-9ddb-4268-aa9d-c2923d64d309"
            },
            "responseBody": {
            	"isDownloadPolicy": "true",
            	"policyNo": "PEID21310116020000000002",
            	"ePolicyURL": "https://iopen-uat.ccic-net.com.cn/management-service/rest/v1/epolicy/download/PEID21310116020000000002-03BDFBF27EE36C520927A7A7A369895DB0A97724A184E6979AF09A715C4E1B73"
            }
        }
    """.trimIndent()

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
        val result = dadiPolicyService.calculatePremium(request)
        println(JsonUtil.encode(result, true))
        Assertions.assertThat(result).isNotNull
    }

    @Test
    fun parse_calculate_premium_response() {
        val resp = JsonUtil.decode(MOCK_CALCULATE_PREMIUM_RESPONSE, DDResp::class.java, DDCResp::class.java)
        println(JsonUtil.encode(resp, true))
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
        val result = dadiPolicyService.underwriting(request)
        println(JsonUtil.encode(result, true))
        Assertions.assertThat(result).isNotNull
    }

    @Test
    fun parse_underwriting_response() {
        val resp = JsonUtil.decode(MOCK_UNDERWRITING_RESPONSE, DDResp::class.java, DDUResp::class.java)
        println(JsonUtil.encode(resp, true))
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
        val result = dadiPolicyService.cancelPolicy(request)
        println(JsonUtil.encode(result, true))
        Assertions.assertThat(result).isNotNull
    }
}