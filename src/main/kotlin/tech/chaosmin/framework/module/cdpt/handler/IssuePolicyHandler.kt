package tech.chaosmin.framework.module.cdpt.handler

import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.CertiTypeEnum
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.GenderEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.enums.CustomerTypeEnum
import tech.chaosmin.framework.module.cdpt.domain.enums.PolicyKhsEnum
import tech.chaosmin.framework.module.cdpt.domain.enums.PolicyStatusEnum
import tech.chaosmin.framework.module.cdpt.entity.*
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyInsuredReq
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyResp
import tech.chaosmin.framework.module.cdpt.helper.convert.PolicyConvert
import java.util.*

/**
 * 接口出单逻辑 <br/>
 * <br/>
 * STEP 1 基础数据校验处理
 * STEP 2 创建/更新订单信息
 * STEP 3 创建保单信息
 * STEP 4 创建/更新保单关联投保人(投保机构)信息
 * STEP 5 创建/更新保单关联被保人信息
 * STEP 6 邮件推送出单(成功/失败)结果
 * <br/>
 * @author Romani min
 * @since 2021/1/27 10:07
 */
@Component
open class IssuePolicyHandler(
    private val basicDataVerificationHandler: BasicDataVerificationHandler,
    private val modifyOrderHandler: ModifyOrderHandler,
    private val modifyPolicyHandler: ModifyPolicyHandler,
    private val modifyPolicyHolderHandler: ModifyPolicyHolderHandler,
    private val modifyPolicyInsurantHandler: ModifyPolicyInsurantHandler,
    private val modifyPolicyKhsHandler: ModifyPolicyKhsHandler
) : AbstractTemplateOperate<PolicyIssueReq, PolicyResp>() {
    override fun validation(arg: PolicyIssueReq, result: RestResult<PolicyResp>) {
        if (arg.startTime == null || arg.endTime == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "订单日期[dateTime]")
        }
        if (arg.productPlanId == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "保险产品[productPlanId]")
        }
        super.validation(arg, result)
    }

    override fun processor(arg: PolicyIssueReq, result: RestResult<PolicyResp>): RestResult<PolicyResp> {
        // step 1
        val validateResult = basicDataVerificationHandler.operate(arg)
        if (!validateResult.success) return result.mapper(validateResult)

        // step 2
        val orderEntity = convert2Order(arg)
        if (arg.orderId == null) orderEntity.save()
        else orderEntity.update(arg.orderId!!)
        modifyOrderHandler.operate(orderEntity)

        // step 3
        val policyEntity = convert2Policy(arg)
        policyEntity.save()
        modifyPolicyHandler.operate(policyEntity)
        // 处理投保人数据
        modifyPolicyHolderHandler.operate(convert2PolicyHolder(arg).apply {
            this.orderId = orderEntity.id
            this.policyId = policyEntity.id
        })
        // 处理被保人数据
        arg.insuredList?.map {
            convert2PolicyInsurant(it).apply {
                this.orderId = orderEntity.id
                this.policyId = policyEntity.id
            }
        }?.map { modifyPolicyInsurantHandler.operate(it) }

        convert2PolicyKhs(arg).forEach {
            modifyPolicyKhsHandler.operate(it)
        }

        // TODO 此处调用保司接口进行实际出单

        val responseData = PolicyConvert.INSTANCE.convert2Resp(policyEntity)
        return result.success(responseData)
    }

    private fun convert2Order(arg: PolicyIssueReq): OrderEntity {
        return OrderEntity().apply {
            this.productPlanId = arg.productPlanId
            this.orderNo = arg.orderNo
            this.startTime = arg.startTime
            this.endTime = arg.endTime
            this.travelDestination = arg.address
            this.extraInfo = arg.remark
        }
    }

    private fun convert2Policy(arg: PolicyIssueReq): PolicyEntity {
        return PolicyEntity().apply {
            this.productPlanId = arg.productPlanId
            this.orderNo = arg.orderNo
            this.effectiveTime = arg.startTime
            this.expiryTime = arg.endTime
            this.travelDestination = arg.address
            this.travelDestination = arg.address
            this.extraInfo = arg.remark
            this.status = PolicyStatusEnum.PROCESS
            this.unitPremium = arg.unitPremium
            this.totalPremium = arg.totalPremium
            this.actualPremium = arg.actualPremium
        }
    }

    private fun convert2PolicyHolder(arg: PolicyIssueReq): PolicyHolderEntity {
        return PolicyHolderEntity().apply {
            this.partyType = CustomerTypeEnum.COMPANY
            this.mainInsuredRelation = 1
            this.name = arg.policyHolderName
            this.certiNo = arg.policyHolderCerti
            this.save()
        }
    }

    private fun convert2PolicyInsurant(arg: PolicyInsuredReq): PolicyInsurantEntity {
        return PolicyInsurantEntity().apply {
            this.partyType = CustomerTypeEnum.PERSON
            this.name = arg.name
            this.certiType = CertiTypeEnum.getFromString(arg.certiType)
            this.certiNo = arg.certiNo
            this.gender = GenderEnum.getFromString(arg.gender)
            this.birthday = arg.dateOfBirth
            this.phoneNo = arg.mobile
            this.save()
        }
    }

    private fun convert2PolicyKhs(arg: PolicyIssueReq): List<PolicyKhsEntity> {
        return if (arg.khsUrl.isNullOrEmpty()) Collections.emptyList()
        else {
            arg.khsUrl!!.map { (key, value) ->
                PolicyKhsEntity().apply {
                    this.orderNo = arg.orderNo
                    this.khsType = PolicyKhsEnum.values().firstOrNull { key == it.name }
                    this.resourceUrl = value
                    this.save()
                }
            }
        }
    }
}