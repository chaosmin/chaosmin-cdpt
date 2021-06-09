package tech.chaosmin.framework.module.cdpt.handler

import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.enums.PolicyKhsEnum
import tech.chaosmin.framework.module.cdpt.entity.PolicyKhsEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyResp
import tech.chaosmin.framework.module.cdpt.helper.convert.IssuerConvert
import tech.chaosmin.framework.module.cdpt.helper.convert.PolicyConvert
import java.util.*

/**
 * 接口出单逻辑 <br/>
 * <br/>
 * STEP 1 基础数据校验处理
 * STEP 2 创建/更新订单信息
 * STEP 3 创建保单信息
 * STEP 4 创建保单关联投保人(投保机构)信息
 * STEP 5 创建保单关联被保人信息
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
        val orderEntity = IssuerConvert.INSTANCE.convert2OrderEntity(arg)
        if (arg.orderId == null) orderEntity.save()
        else orderEntity.update(arg.orderId!!)
        modifyOrderHandler.operate(orderEntity)

        // step 3
        val policyEntity = IssuerConvert.INSTANCE.convert2PolicyEntity(arg)
        policyEntity.save()
        modifyPolicyHandler.operate(policyEntity)

        // 处理投保人数据
        policyEntity.holder?.run {
            this.policyId = policyEntity.id
            modifyPolicyHolderHandler.operate(this)
        }

        // 处理被保人数据
        policyEntity.insuredList?.forEach {
            it.policyId = policyEntity.id
            modifyPolicyInsurantHandler.operate(it)
        }

        // 2021-06-08 18:57:06 处理可回溯信息
        policyEntity.khsList = convert2PolicyKhs(arg)
        policyEntity.khsList?.forEach {
            it.policyId = policyEntity.id
            modifyPolicyKhsHandler.operate(it)
        }

        // TODO 此处调用保司接口进行实际出单

        val responseData = PolicyConvert.INSTANCE.convert2Resp(policyEntity)
        return result.success(responseData)
    }

    private fun convert2PolicyKhs(arg: PolicyIssueReq): List<PolicyKhsEntity> {
        return if (arg.khsUrl.isNullOrEmpty()) Collections.emptyList()
        else {
            arg.khsUrl!!.map { (key, value) ->
                PolicyKhsEntity().apply {
                    this.khsType = PolicyKhsEnum.values().firstOrNull { key == it.name }
                    this.resourceUrl = value
                    this.save()
                }
            }
        }
    }
}