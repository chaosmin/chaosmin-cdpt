package tech.chaosmin.framework.module.cdpt.handler

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.enums.OrderStatusEnum
import tech.chaosmin.framework.module.cdpt.domain.enums.PolicyProcessEnum
import tech.chaosmin.framework.module.cdpt.domain.enums.PolicyStatusEnum
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.DDResp
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj.DDCResp
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj.DDUResp
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyResp
import tech.chaosmin.framework.module.cdpt.helper.convert.ChannelRequestConvert
import tech.chaosmin.framework.module.cdpt.helper.convert.IssuerConvert
import tech.chaosmin.framework.module.cdpt.helper.convert.PolicyConvert
import tech.chaosmin.framework.module.cdpt.service.external.impl.DadiChannelRequestService
import tech.chaosmin.framework.module.cdpt.service.inner.OrderTempService
import tech.chaosmin.framework.module.cdpt.service.inner.PolicyKhsService
import tech.chaosmin.framework.utils.JsonUtil

/**
 * 接口出单逻辑 <p>
 * <p>
 * STEP 1 基础数据校验处理
 * STEP 2 创建/更新订单信息
 * STEP 3 创建保单信息
 * STEP 4 创建保单关联投保人(投保机构)信息
 * STEP 5 创建保单关联被保人信息
 * STEP 6 邮件推送出单(成功/失败)结果
 * <p>
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
    private val orderTempService: OrderTempService,
    private val policyKhsService: PolicyKhsService,
    private val dadiChannelRequestService: DadiChannelRequestService
) : AbstractTemplateOperate<PolicyIssueReq, PolicyResp>() {
    private val logger = LoggerFactory.getLogger(IssuePolicyHandler::class.java)

    override fun validation(arg: PolicyIssueReq, result: RestResult<PolicyResp>) {
        if (arg.startTime == null || arg.endTime == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "订单日期[dateTime]")
        }
        if (arg.orderNo == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "订单号[orderNo]")
        }
        if (arg.goodsPlanId == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "保险产品[goodsPlanId]")
        }
        super.validation(arg, result)
    }

    override fun processor(arg: PolicyIssueReq, result: RestResult<PolicyResp>): RestResult<PolicyResp> {
        // step 1 简易数据校验及数据补充
        val validateResult = basicDataVerificationHandler.operate(arg)
        if (!validateResult.success) return result.mapper(validateResult)

        // step 2.1 创建保司下单请求报文
        orderTempService.saveOrUpdate(arg.orderNo!!, JsonUtil.encode(arg))
        // TODO 请求保司接口超时兜底处理逻辑开发
        val policyEntity = IssuerConvert.INSTANCE.convert2PolicyEntity(arg)
        val ddReq = ChannelRequestConvert.convert2DDCReq(policyEntity)
        val ddcRespEntity = dadiChannelRequestService.request(PolicyProcessEnum.PREMIUM_TRIAL, ddReq) {
            JsonUtil.decode(it, DDResp::class.java, DDCResp::class.java)
        }
        if ("1" == ddcRespEntity?.responseHead?.resultCode) {
            // 请求大地下单接口失败, 返回页面异常
            return RestResultExt.failureRestResult(msg = ddcRespEntity.responseHead?.resultMessage)
        }
        val proposalNo = (ddcRespEntity?.responseBody as DDCResp).proposalNo
        policyEntity.proposalNo = proposalNo
        // step 2.2 保存订单状态
        val orderEntity = IssuerConvert.INSTANCE.convert2OrderEntity(arg)
        orderEntity.status = OrderStatusEnum.INIT
        orderEntity.proposalNo = proposalNo
        if (arg.orderId == null) orderEntity.save() else orderEntity.update()
        val moResult = modifyOrderHandler.operate(orderEntity)
        if (!moResult.success) return result.mapper(moResult)
        val ddReq1 = ChannelRequestConvert.convert2DDUReq(policyEntity)
        val dduRespEntity = dadiChannelRequestService.request(PolicyProcessEnum.UNDERWRITING, ddReq1) {
            JsonUtil.decode(it, DDResp::class.java, DDUResp::class.java)
        }
        if ("1" == dduRespEntity?.responseHead?.resultCode) {
            orderEntity.status = OrderStatusEnum.FAILED
            orderEntity.update()
            modifyOrderHandler.operate(orderEntity)
            return RestResultExt.failureRestResult(msg = dduRespEntity.responseHead?.resultMessage)
        }
        // 处理订单数据
        orderEntity.status = OrderStatusEnum.SUCCESS
        orderEntity.update()
        modifyOrderHandler.operate(orderEntity)

        policyEntity.run {
            this.status = PolicyStatusEnum.SUCCESS
            this.policyNo = (dduRespEntity?.responseBody as DDUResp).policyNo
            this.ePolicyUrl = (dduRespEntity.responseBody as DDUResp).ePolicyURL
            this.save()
            val (_, _, _, _, success) = modifyPolicyHandler.operate(this)
            if (!success) {
                logger.error("Fail To save policy info, please do data patch on this record! # ${JsonUtil.encode(this)}")
            }
        }

        // 处理投保人数据
        policyEntity.holder?.run {
            this.save()
            this.policyId = policyEntity.id
            val (_, _, _, _, success) = modifyPolicyHolderHandler.operate(this)
            if (!success) {
                logger.error("Fail To save policy holder info, please do data patch on this record! # ${JsonUtil.encode(this)}")
            }
        }

        // 处理被保人数据
        policyEntity.insuredList?.forEach {
            it.save()
            it.policyId = policyEntity.id
            val (_, _, _, _, success) = modifyPolicyInsurantHandler.operate(it)
            if (!success) {
                logger.error("Fail To save policy insurant info, please do data patch on this record! # ${JsonUtil.encode(it)}")
            }
        }

        // 关联可回溯文件的订单号及保单ID
        policyKhsService.linkOrderAndPolicy(arg.orderNo!!, policyEntity.id!!)

        // 返回落地的保单数据
        val responseData = PolicyConvert.INSTANCE.convert2Resp(policyEntity)
        return result.success(responseData)
    }
}