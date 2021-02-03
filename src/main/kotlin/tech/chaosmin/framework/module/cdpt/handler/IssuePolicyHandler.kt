package tech.chaosmin.framework.module.cdpt.handler

import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.CertiTypeEnum
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.GenderEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.enums.CustomerTypeEnum
import tech.chaosmin.framework.module.cdpt.domain.enums.OrderStatusEnum
import tech.chaosmin.framework.module.cdpt.domain.enums.PolicyStatusEnum
import tech.chaosmin.framework.module.cdpt.entity.OrderEntity
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.entity.PolicyHolderEntity
import tech.chaosmin.framework.module.cdpt.entity.PolicyInsurantEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyInsuredReq
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyResp
import tech.chaosmin.framework.module.cdpt.helper.convert.PolicyConvert
import tech.chaosmin.framework.utils.BizNoUtil

/**
 * @author Romani min
 * @since 2021/1/27 10:07
 */
@Component
open class IssuePolicyHandler(
    private val modifyOrderHandler: ModifyOrderHandler,
    private val modifyPolicyHandler: ModifyPolicyHandler,
    private val modifyPolicyHolderHandler: ModifyPolicyHolderHandler,
    private val modifyPolicyInsurantHandler: ModifyPolicyInsurantHandler
) : AbstractTemplateOperate<PolicyIssueReq, PolicyResp>() {
    override fun validation(arg: PolicyIssueReq, result: RestResult<PolicyResp>) {
        if (arg.startTime == null || arg.endTime == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "dateTime")
        }
        if (arg.productPlanId == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "productPlanId")
        }
        super.validation(arg, result)
    }

    override fun processor(arg: PolicyIssueReq, result: RestResult<PolicyResp>): RestResult<PolicyResp> {
        val orderEntity = if (arg.orderId == null) convert2Order(arg)
        else OrderEntity().apply {
            this.status = OrderStatusEnum.SUCCESS
            this.update(arg.orderId!!)
        }
        modifyOrderHandler.operate(orderEntity)
        val policyEntity = convert2Policy(arg)
        modifyPolicyHandler.operate(policyEntity)
        modifyPolicyHolderHandler.operate(convert2PolicyHolder(arg).apply {
            this.orderId = orderEntity.id
            this.policyId = policyEntity.id
        })
        arg.insuredList?.map {convert2PolicyInsurant(it).apply {
            this.orderId = orderEntity.id
            this.policyId = policyEntity.id
        } }?.map { modifyPolicyInsurantHandler.operate(it) }
        // TODO 此处调用保司接口进行实际出单
        val responseData = PolicyConvert.INSTANCE.convert2Resp(policyEntity)
        return result.success(responseData)
    }

    private fun convert2Order(arg: PolicyIssueReq): OrderEntity {
        arg.orderNo = BizNoUtil.generateOrderNo(arg.productPlanId!!)
        return OrderEntity().apply {
            this.productPlanId = arg.productPlanId
            this.orderNo = arg.orderNo
            this.startTime = arg.startTime
            this.endTime = arg.endTime
            this.travelDestination = arg.address
            this.extraInfo = arg.remark
            this.status = OrderStatusEnum.SUCCESS
            this.save()
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
            // TODO 后期这里需要在后台重新计算
            this.unitPremium = arg.unitPremium
            this.totalPremium = arg.totalPremium
            this.actualPremium = arg.actualPremium
            this.save()
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
}