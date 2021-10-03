/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PolicyUnderwritingHandler.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.handler

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.PolicyProcessEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.api.convert.ChannelRequestConvert
import tech.chaosmin.framework.module.cdpt.api.convert.IssuerConvert
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.DDResp
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj.DDCResp
import tech.chaosmin.framework.module.cdpt.entity.enums.OrderStatusEnum
import tech.chaosmin.framework.module.cdpt.entity.enums.PayStatusEnum
import tech.chaosmin.framework.module.cdpt.entity.enums.PayTypeEnum
import tech.chaosmin.framework.module.cdpt.entity.enums.PolicyStatusEnum
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.logic.outer.partner.DadiInsurerService
import tech.chaosmin.framework.utils.JsonUtil
import tech.chaosmin.framework.utils.SecurityUtil
import java.util.*

/**
 * @author Romani min
 * @since 2021/9/4 12:49
 */
@Component
open class PolicyUnderwritingHandler(
    private val orderModifyHandler: OrderModifyHandler,
    private val policyCheckHandler: PolicyCheckHandler,
    private val dadiInsurerService: DadiInsurerService,
    private val rabbitTemplate: RabbitTemplate
) : AbstractTemplateOperate<PolicyIssueReq, PolicyEntity>() {
    private val logger = LoggerFactory.getLogger(PolicyUnderwritingHandler::class.java)

    @Value("\${test.mock:false}")
    private val mock: Boolean = false

    override fun validation(arg: PolicyIssueReq, res: RestResult<PolicyEntity>) {
        if (arg.startTime == null || arg.endTime == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL, "订单日期[dateTime]")
        }
        if (arg.orderNo == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL, "订单号[orderNo]")
        }
        if (arg.goodsPlanId == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL, "保险产品[goodsPlanId]")
        }
    }

    override fun processor(arg: PolicyIssueReq, res: RestResult<PolicyEntity>): RestResult<PolicyEntity> {
        val checkResult = policyCheckHandler.operate(arg)
        // 数据校验失败, 直接返回结果
        if (!checkResult.success) return RestResultExt.mapper(checkResult)
        // 保存投保单信息
        val policyEn = IssuerConvert.INSTANCE.convert2PolicyEntity(arg)
        policyEn.userId = SecurityUtil.getUserId()
        policyEn.status = PolicyStatusEnum.TO_BE_INSURED
        policyEn.payStatus = PayStatusEnum.TO_BE_PAID

        // TODO 此处为大地保险特殊处理逻辑, 后续接入其他保司需要注意
        // 如果被保人数量为2时, 需要分开出单
        if (policyEn.insuredList?.size == 2) {
            // 重新设置被保人数
            policyEn.insuredSize = 1
            // 重新计算原价保费及结算保费
            policyEn.totalPremium = policyEn.totalPremium?.div(2)
            policyEn.actualPremium = policyEn.actualPremium?.div(2)
            // 循环被保人列表出单
            policyEn.insuredList?.forEachIndexed { index, it ->
                // 修改幂等建
                policyEn.orderNo = "${arg.orderNo}-${index + 1}"
                // 重新设置被保人列表
                policyEn.insuredList = Collections.singletonList(it)
                issuePolicy(policyEn)
            }
        } else issuePolicy(policyEn)
        return RestResultExt.successRestResult(policyEn)
    }

    private fun issuePolicy(policyEn: PolicyEntity) {
        // 第三方保司核保
        // TODO 目前仅支持大地保险一家, 后续考虑抽成通用
        val response = dadiInsurerService.request(PolicyProcessEnum.UNDERWRITING, ChannelRequestConvert.convert2DDCReq(policyEn)) {
            val str = if (mock) {
                """{"responseHead":{"responseTime":"2021-07-09T13:22:52","resultCode":"0","resultMessage":"下单接口任务执行成功！","responseId":"a6aa0ab4-6b4a-4085-9026-b778201fdc82"},"responseBody":{"isEProposal":"Y","policyNature":"01","beforeVatPremium":23.56,"judicalScopeCode":"01","productName":"大地行出境人员意外伤害保险","isPackageProduct":"N","comPostCode":"200080","certificatePayMentType":"","sumInsured":1607800,"belongToHandler2Name":"王溃溃","issueUserCode":"8000579613","businessSourceCode":"0101","siCurrencyCode":"CNY","channelOpInfoList":[{"channelComCode":"31011500","channelSeqNo":"202107091322060003","channelProductCode":"EGC","trxDate":"2021-07-09","channelCode":"E00149-002"}],"isAutoUw":"Y","sequenceNumber":1,"issueUserName":"钟思妮","orgName":"上海分公司营业部银保渠道部","totalDiscountRate":0,"groupBusinessCode":"02","sumMainPrem":9.1,"businessAttribute":"E00149","proposalNo":"TEGC21310116020000000050","premiumBookExchangeRate":1,"salesName":"上海分公司营业部银保渠道部","proposalStatus":"262","shortRateType":"04","policyHolderName":"堡垒","operateDate":"2021-07-09","proposalDate":"2021-07-09","siLocalExchangeRate":1,"sumSubPrem":15.9,"electronicPolicyTemplateId":"form032_1_N","duePremium":25,"secondLine":72000,"internalCoInsuranceType":"01","policyCustomerList":[{"customerType":"01","nationalityCode":"CHN","partyCategory":"02","idType":"803","customerRoleCode":"1","idNo":"123456","customerName":"堡垒"}],"coInsuranceType":"01","issueOrgCode":"31011602","policyStatus":1,"isSpecialBusiness":"N","policyLobList":[{"totalInsuredCount":1,"rescueCompanyCode":"2303285836123","policyRiskList":[{"personInsuredList":[{"sequenceNumber":1,"benefitModeCode":"1","idType":"414","duePremium":25,"dateOfBirth":"1980-06-12","isOnJob":"N","idNo":"ETJW01","customerName":"张三","keyCoveragePlanGroup":"1","nationalityCode":"CHN","partyCategory":"01","polHolderInsuredRelaCode":"80","customerRoleCode":"2","numberOfCopies":1,"hasAffilInsured":"N","indiGenderCode":"1","age":41}],"insuredGroupList":[{"insuredGroupNo":1,"duePremium":25,"policyCoverageList":[{"clauseCode":"CF2100218","sequenceNumber":13000,"shortRate":1,"kindCode":"C100552","vatRateType":"A","vatRate":0.06,"duePremium":0.5,"premiumRate":0.0005,"policyStatus":2,"isNonDeductible":"N","sumInsured":1000,"avgSumInsured":1000,"isFinalLevelCt":"Y","unitPremium":0.5,"clauseName":"附加旅程取消保险条款"},{"clauseCode":"CF2100223","kindCode":"C100503","vatRateType":"A","vatRate":0.06,"duePremium":0.8,"premiumRate":0.00053333333,"policyStatus":2,"sumInsured":1500,"isFinalLevelCt":"Y","unitPremium":0.8,"clauseName":"中国大地保险附加通用住院补贴医疗保险条款","sequenceNumber":9000,"shortRate":1,"subsidyAmount":"50","isNonDeductible":"N","avgSumInsured":1500,"subsidyDay":"30"},{"clauseCode":"CF2100194","sequenceNumber":7000,"shortRate":1,"kindCode":"C100417","vatRateType":"A","vatRate":0.06,"duePremium":0.5,"premiumRate":0.000005,"policyStatus":2,"isNonDeductible":"N","sumInsured":100000,"avgSumInsured":100000,"isFinalLevelCt":"Y","unitPremium":0.5,"clauseName":"中国大地保险附加突发急性病身故保险条款"},{"clauseCode":"CF2100216","sequenceNumber":8000,"shortRate":1,"kindCode":"C100550","vatRateType":"A","vatRate":0.06,"duePremium":5,"premiumRate":0.0000625,"policyStatus":2,"isNonDeductible":"N","sumInsured":80000,"avgSumInsured":80000,"isFinalLevelCt":"Y","unitPremium":5,"clauseName":"中国大地保险附加境外旅行医疗保险条款"},{"clauseCode":"CF2100231","sequenceNumber":18000,"shortRate":1,"kindCode":"C100543","vatRateType":"A","vatRate":0.06,"duePremium":0.1,"premiumRate":0.0001,"policyStatus":2,"isNonDeductible":"N","sumInsured":1000,"avgSumInsured":1000,"isFinalLevelCt":"Y","unitPremium":0.1,"clauseName":"附加家居保障保险条款"},{"clauseCode":"CF2100230","sequenceNumber":19000,"shortRate":1,"kindCode":"C100542","vatRateType":"A","vatRate":0.06,"duePremium":0.5,"premiumRate":0.000005,"policyStatus":2,"isNonDeductible":"N","sumInsured":100000,"avgSumInsured":100000,"isFinalLevelCt":"Y","unitPremium":0.5,"clauseName":"附加个人第三者责任保险条款"},{"clauseCode":"CF2100256","sequenceNumber":15000,"shortRate":1,"kindCode":"C100560","vatRateType":"A","vatRate":0.06,"duePremium":0.8,"premiumRate":0.0008,"policyStatus":2,"isNonDeductible":"N","sumInsured":1000,"avgSumInsured":1000,"isFinalLevelCt":"Y","unitPremium":0.8,"clauseName":"附加行李延误保险条款（B款）"},{"clauseCode":"CF2100212","sequenceNumber":10000,"shortRate":1,"kindCode":"C100540","vatRateType":"A","vatRate":0.06,"duePremium":2,"premiumRate":0.00002,"policyStatus":2,"isNonDeductible":"N","sumInsured":100000,"avgSumInsured":100000,"isFinalLevelCt":"Y","unitPremium":2,"clauseName":"中国大地保险附加医疗转运与送返医疗保险条款"},{"clauseCode":"CF2100233","sequenceNumber":17000,"shortRate":1,"kindCode":"C100547","vatRateType":"A","vatRate":0.06,"duePremium":0.9,"premiumRate":0.0009,"policyStatus":2,"isNonDeductible":"N","sumInsured":1000,"avgSumInsured":1000,"isFinalLevelCt":"Y","unitPremium":0.9,"clauseName":"附加个人行李及随身物品损失保险条款"},{"clauseCode":"CF1100278","sequenceNumber":1000,"shortRate":1,"kindCode":"C100436","vatRateType":"A","vatRate":0.06,"duePremium":7.2,"premiumRate":0.000036,"policyStatus":2,"isNonDeductible":"N","sumInsured":200000,"disabilityStandard":"3","avgSumInsured":200000,"isFinalLevelCt":"Y","unitPremium":7.2,"clauseName":"大地行出境意外伤害保险条款"},{"clauseCode":"CF2100228","sequenceNumber":11000,"shortRate":1,"kindCode":"C100562","vatRateType":"A","vatRate":0.06,"duePremium":1.1,"premiumRate":0.000011,"policyStatus":2,"isNonDeductible":"N","sumInsured":100000,"avgSumInsured":100000,"isFinalLevelCt":"Y","unitPremium":1.1,"clauseName":"附加遗体送返保险条款"},{"clauseCode":"CF2100269","sequenceNumber":6000,"shortRate":1,"kindCode":"C100620","vatRateType":"A","vatRate":0.06,"duePremium":0.2,"premiumRate":0.000002,"policyStatus":2,"isNonDeductible":"N","sumInsured":100000,"disabilityStandard":"3","avgSumInsured":100000,"isFinalLevelCt":"Y","unitPremium":0.2,"clauseName":"附加自驾意外伤害保险条款"},{"clauseCode":"CF2100229","sequenceNumber":12000,"shortRate":1,"kindCode":"C100563","vatRateType":"A","vatRate":0.06,"duePremium":0.8,"premiumRate":0.00004,"policyStatus":2,"isNonDeductible":"N","sumInsured":20000,"avgSumInsured":20000,"isFinalLevelCt":"Y","unitPremium":0.8,"clauseName":"中国大地保险附加住院探望费用补偿保险条款"},{"clauseCode":"CF2100220","sequenceNumber":14000,"shortRate":1,"kindCode":"C100554","vatRateType":"A","vatRate":0.06,"duePremium":2,"premiumRate":0.00666666667,"policyStatus":2,"isNonDeductible":"N","sumInsured":300,"avgSumInsured":300,"isFinalLevelCt":"Y","unitPremium":2,"clauseName":"附加旅程延误保险条款"},{"clauseCode":"CF1100290","sequenceNumber":4000,"shortRate":1,"kindCode":"C100468","vatRateType":"A","vatRate":0.06,"duePremium":0.3,"premiumRate":0.0000015,"policyStatus":2,"isNonDeductible":"N","sumInsured":200000,"disabilityStandard":"3","avgSumInsured":200000,"isFinalLevelCt":"Y","unitPremium":0.3,"clauseName":"大地通达公共交通工具意外伤害保险条款"},{"clauseCode":"CF1100290","sequenceNumber":2000,"shortRate":1,"kindCode":"C100590","vatRateType":"A","vatRate":0.06,"duePremium":0.8,"premiumRate":0.000004,"policyStatus":2,"isNonDeductible":"N","sumInsured":200000,"disabilityStandard":"3","avgSumInsured":200000,"isFinalLevelCt":"Y","unitPremium":0.8,"clauseName":"大地通达公共交通工具意外伤害保险条款"},{"clauseCode":"CF1100290","sequenceNumber":5000,"shortRate":1,"kindCode":"C100470","vatRateType":"A","vatRate":0.06,"duePremium":0.3,"premiumRate":0.0000015,"policyStatus":2,"isNonDeductible":"N","sumInsured":200000,"disabilityStandard":"3","avgSumInsured":200000,"isFinalLevelCt":"Y","unitPremium":0.3,"clauseName":"大地通达公共交通工具意外伤害保险条款"},{"clauseCode":"CF1100290","sequenceNumber":3000,"shortRate":1,"kindCode":"C100469","vatRateType":"A","vatRate":0.06,"duePremium":0.5,"premiumRate":0.0000025,"policyStatus":2,"isNonDeductible":"N","sumInsured":200000,"disabilityStandard":"3","avgSumInsured":200000,"isFinalLevelCt":"Y","unitPremium":0.5,"clauseName":"大地通达公共交通工具意外伤害保险条款"},{"clauseCode":"CF2100213","sequenceNumber":16000,"shortRate":1,"kindCode":"C100541","vatRateType":"A","vatRate":0.06,"duePremium":0.7,"premiumRate":0.00035,"policyStatus":2,"isNonDeductible":"N","sumInsured":2000,"avgSumInsured":2000,"isFinalLevelCt":"Y","unitPremium":0.7,"clauseName":"附加境外旅行证件重置保险条款"}],"planName":"大地行出境人员意外伤害保险-海外计划一","numberOfCopies":1,"planCode":"EGC2150028","insuredCount":1}]}],"rescueModelCode":"02","rescuePhone":"86-21-61297908","ahPaymentMethodCode":"02","policyFormList":[{"customFormNo":"A00065","sequenceNumber":1,"customFormTitle":"自编特约","formCategory":"2","customFormContent":"1、在保险期间内，被保险人旅行期间遭受意外或者突发急性病，在二级以上（含）公立医院治疗由该意外引致的伤害或者该急性病，保险人就该意外或者该急性病发生之日起九十日所支出的符合当地城镇职工基本医疗保险规定的支/给付范围和标准的（被保险人系境外旅游的，境外治疗不在此限）、医学必要的医疗费用（以下简称“每次事故合理医疗费用”），按“（每次事故合理医疗费用-人民币0元）×100%”给付意外医疗或者急性病医疗保险金。\r2、本保单投保年龄为30天-85周岁。针对其中70周岁以上的被保险人，保险人承担的“意外身故及伤残”责任限额为10万、“突发急性病身故/新冠肺炎身故”的责任限额为5万元。\r3、旅程延误：每延误5小时赔付300元，以保额为限。\r4、行李延误：每延误6小时赔付500元，以保额为限。\r5、个人行李及随身物品损失：每件或每套行李物品限额1000元。\r6、未成年人赔偿限额按保监会规定。\r7、若投保一年期保单，被保险人在保险期间内多次出行的，保险人就每次旅行最多承担出境之日起至第90天的保险责任。\r8、本方案每人限投一份，多投无效。"},{"customFormNo":"A00116","sequenceNumber":2,"customFormTitle":"伤残特约（行标）","formCategory":"2","customFormContent":"被保险人自遭受该意外之日起一百八十日内以该意外为直接、完全原因而导致《人身保险伤残评定标准及代码》（JR/T 0083—2013，保监发[2014]6号）中所列伤残的，保险人按该处残疾的伤残等级对应的给付比例和该被保险人的意外伤害保险金额的乘积给付意外伤残保险金。伤残等级对应的保险金给付比例分为十档，伤残程度第一级对应的保险金给付比例为100%，伤残程度第十级对应的保险金给付比例为10%，每级相差10%。"}]}],"expiryDate":"2021-07-10T23:59:59","regionCode":"310000","isRenewable":"Y","isSendSms":"N","orgCode":"31011504","bookCurrency":"CNY","policyPaymentInfoList":[{"sequenceNumber":1,"installmentPeriodCount":1,"installmentList":[{"sequenceNumber":1,"installmentDate":"2021-07-10","installmentAmount":25,"dueDate":"2021-07-10","installmentPeriodSeq":1,"serialNo":1}]}],"premiumCurrencyCode":"CNY","shortRate":1,"businessSource2Code":"0101","invoiceInfoList":[{"customerType":"1","taxPayerType":"4","companyName":"堡垒","invoiceType":"2","copyDataFromType":"1"}],"renewalType":"1","idCheckResultCode":"100","localCurrencyCode":"CNY","isSavePlanDetail":"N","issueOrgName":"浦东支公司综合部","comAddress":"上海市虹口区吴淞路130号704室","businessCate":"1","insuredListType":"1","productCode":"EGC","cessionBasisCode":2,"policyType":"1","channelCodeByPerson":"C0000016","effectiveDate":"2021-07-10T00:00:00","belongToHandler2Code":"8000500301"}}"""
            } else it
            JsonUtil.decode(str, DDResp::class.java, DDCResp::class.java)
        }
        // 核保失败, 直接返回异常信息
        if ("1" == response?.responseHead?.resultCode) {
            throw FrameworkException(ErrorCodeEnum.BUSINESS_ERROR.code, response.responseHead?.resultMessage ?: "请求第三方下单接口异常")
        }
        // 保司投保单号
        val proposalNo = (response?.responseBody as DDCResp).proposalNo
        policyEn.status = PolicyStatusEnum.UNDERWRITING_PASS
        policyEn.proposalNo = proposalNo
        // 发布消息新建保单对象
        logger.info("Send async message to queue [policy], message: ${JsonUtil.encode(policyEn)}")
        rabbitTemplate.convertAndSend("policy", policyEn.save())
        // 如果是线下承保, 则后台自动进行后续流程
        if (policyEn.payType == PayTypeEnum.OFFLINE) {
            orderModifyHandler.saveOrUpdate(policyEn.orderNo!!, OrderStatusEnum.PROCESSING)
            logger.info("Send message to queue [insurer], message: ${JsonUtil.encode(policyEn)}")
            rabbitTemplate.convertAndSend("insurer", policyEn)
        } else {
            orderModifyHandler.saveOrUpdate(policyEn.orderNo!!, OrderStatusEnum.TO_BE_PAID)
        }
    }
}