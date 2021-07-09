package tech.chaosmin.framework.module.cdpt.handler

import org.slf4j.LoggerFactory
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.OrderStatusEnum
import tech.chaosmin.framework.base.enums.PolicyProcessEnum
import tech.chaosmin.framework.base.enums.PolicyStatusEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.DDResp
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj.DDCResp
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj.DDUResp
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyResp
import tech.chaosmin.framework.module.cdpt.helper.convert.ChannelRequestConvert
import tech.chaosmin.framework.module.cdpt.helper.convert.IssuerConvert
import tech.chaosmin.framework.module.cdpt.service.external.impl.DadiChannelRequestService
import tech.chaosmin.framework.module.cdpt.service.inner.OrderTempService
import tech.chaosmin.framework.module.cdpt.service.inner.PolicyKhsService
import tech.chaosmin.framework.utils.JsonUtil
import tech.chaosmin.framework.utils.SecurityUtil
import java.util.*

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
        val orderEntity = IssuerConvert.INSTANCE.convert2OrderEntity(arg)

        Thread(DelegatingSecurityContextRunnable(fun() {
            orderTempService.saveOrUpdate(arg.orderNo!!, JsonUtil.encode(arg))
            orderEntity.status = OrderStatusEnum.INIT
            orderEntity.save()
            modifyOrderHandler.operate(orderEntity)
        })).start()

        return try {
            val policyEntity = IssuerConvert.INSTANCE.convert2PolicyEntity(arg)
            policyEntity.userId = SecurityUtil.getUserId()
            // orderEntity.status = OrderStatusEnum.SUCCESS
            // 2021-06-29 23:29:03 当被保人数为2人时, 拆分被保人列表, 分为两个个单进行处理
            if (policyEntity.insuredList?.size == 2) {
                // 重新设置被保人数
                policyEntity.insuredSize = 1
                // 重新计算原价保费及结算保费
                policyEntity.totalPremium = policyEntity.totalPremium?.div(2)
                policyEntity.actualPremium = policyEntity.actualPremium?.div(2)
                // 循环被保人列表出单
                policyEntity.insuredList?.forEachIndexed { index, it ->
                    // 修改幂等建
                    policyEntity.orderNo = "${arg.orderNo}-${index + 1}"
                    // 重新设置被保人列表
                    policyEntity.insuredList = Collections.singletonList(it)
                    issuePolicy(policyEntity)
                }
            } else issuePolicy(policyEntity)
            orderEntity.status = OrderStatusEnum.SUCCESS
            result.success()
        } catch (e: FrameworkException) {
            orderEntity.status = OrderStatusEnum.FAILED
            RestResultExt.failureRestResult(msg = e.message)
        } finally {
            orderEntity.update()
            modifyOrderHandler.operate(orderEntity)
        }
    }

    private fun issuePolicy(policyEntity: PolicyEntity) {
        val ddReq = ChannelRequestConvert.convert2DDCReq(policyEntity)
        val ddcRespEntity = dadiChannelRequestService.request(PolicyProcessEnum.PREMIUM_TRIAL, ddReq) {
            JsonUtil.decode(it, DDResp::class.java, DDCResp::class.java)
        }
        if ("1" == ddcRespEntity?.responseHead?.resultCode) {
            throw FrameworkException(ErrorCodeEnum.BUSINESS_ERROR.code, ddcRespEntity.responseHead?.resultMessage ?: "请求第三方下单接口异常")
        }
        val proposalNo = (ddcRespEntity?.responseBody as DDCResp).proposalNo

        policyEntity.proposalNo = proposalNo
        val ddReq1 = ChannelRequestConvert.convert2DDUReq(policyEntity)
        val dduRespEntity = dadiChannelRequestService.request(PolicyProcessEnum.UNDERWRITING, ddReq1) {
            JsonUtil.decode(it, DDResp::class.java, DDUResp::class.java)
        }
        if ("1" == dduRespEntity?.responseHead?.resultCode) {
            throw FrameworkException(ErrorCodeEnum.BUSINESS_ERROR.code, dduRespEntity.responseHead?.resultMessage ?: "请求第三方核保接口异常")
        }

        Thread(DelegatingSecurityContextRunnable(fun() {
            // 保存保单信息
            policyEntity.run {
                // 清除ID确保每次都会新建保单信息
                this.id = null
                this.orderNo = this.orderNo?.substringBefore("-")
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
                // 清除ID确保每次都会新建保单信息
                this.id = null
                this.save()
                this.policyId = policyEntity.id
                val (_, _, _, _, success) = modifyPolicyHolderHandler.operate(this)
                if (!success) {
                    logger.error("Fail To save policy holder info, please do data patch on this record! # ${JsonUtil.encode(this)}")
                }
            }

            // 处理被保人数据
            policyEntity.insuredList?.forEach {
                // 清除ID确保每次都会新建保单信息
                it.id = null
                it.save()
                it.policyId = policyEntity.id
                val (_, _, _, _, success) = modifyPolicyInsurantHandler.operate(it)
                if (!success) {
                    logger.error("Fail To save policy insurant info, please do data patch on this record! # ${JsonUtil.encode(it)}")
                }
            }

            // 关联可回溯信息
            policyKhsService.linkOrderAndPolicy(policyEntity.orderNo!!, policyEntity.id!!)
        })).start()
    }
}