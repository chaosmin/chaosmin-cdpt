/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api.provider

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.AbstractAPI
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.api.OrderAPI
import tech.chaosmin.framework.module.cdpt.api.convert.OrderConvert
import tech.chaosmin.framework.module.cdpt.api.convert.OrderTraceConvert
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.OrderEx
import tech.chaosmin.framework.module.cdpt.entity.OrderEntity
import tech.chaosmin.framework.module.cdpt.entity.enums.OrderStatusEnum
import tech.chaosmin.framework.module.cdpt.entity.request.OrderReq
import tech.chaosmin.framework.module.cdpt.entity.request.OrderTraceReq
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.entity.response.OrderResp
import tech.chaosmin.framework.module.cdpt.logic.handler.OrderModifyHandler
import tech.chaosmin.framework.module.cdpt.logic.handler.OrderTraceModifyHandler
import tech.chaosmin.framework.module.cdpt.logic.interrogator.OrderInterrogator
import tech.chaosmin.framework.module.payment.entity.wechat.request.NativePayReq
import tech.chaosmin.framework.module.payment.service.WechatNativePayService
import tech.chaosmin.framework.utils.JsonUtil
import java.math.BigDecimal
import java.util.concurrent.Executor

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class OrderProvider(
    private val taskExecutor: Executor,
    private val orderInterrogator: OrderInterrogator,
    private val orderModifyHandler: OrderModifyHandler,
    private val orderTraceModifyHandler: OrderTraceModifyHandler,
    private val wechatNativePayService: WechatNativePayService
) : AbstractAPI<OrderEx, OrderEntity, OrderReq, OrderResp>(
    OrderConvert.INSTANCE, orderInterrogator, orderModifyHandler
), OrderAPI {
    private val logger = LoggerFactory.getLogger(OrderProvider::class.java)

    override fun payment(orderNo: String): RestResult<String> {
        val orderEntity = orderInterrogator.page(PageQuery.eqOne("order.order_no", orderNo)).records.firstOrNull()
            ?: return RestResultExt.successRestResult("未查询到该订单信息")
        val nativeReq = NativePayReq().apply {
            this.out_trade_no = orderEntity.orderNo
            this.description = "${orderEntity.productName} ${orderEntity.proposalNo}"
            this.amount = Amount().apply {
                val premium = BigDecimal(orderEntity.actualPremium!!)
                // 设置扣费金额, 转化为分
                this.total = premium.multiply(BigDecimal(100)).toInt()
            }
        }
        logger.info("创建微信支付订单: ${JsonUtil.encode(nativeReq)}")
        val result = wechatNativePayService.createOrder(nativeReq)
        logger.info("创建微信订单返回: $result")
        return RestResultExt.successRestResult(data = result)
    }

    override fun saveOrderTrace(orderNo: String, req: OrderTraceReq): RestResult<String> {
        taskExecutor.execute {
            val policyTraceEntity = OrderTraceConvert.INSTANCE.toEn(req)
            policyTraceEntity.orderNo = orderNo
            orderTraceModifyHandler.operate(policyTraceEntity.save())
        }
        return RestResultExt.successRestResult()
    }

    override fun getDraft(orderNo: String): RestResult<PolicyIssueReq> {
        val draftJson = orderInterrogator.loadDraft(orderNo)
        // 反序列化成投保参数, 返回前端加载
        val issueReq = JsonUtil.decode(draftJson, PolicyIssueReq::class.java)!!
        return RestResultExt.successRestResult(issueReq)
    }

    override fun saveDraft(orderNo: String, req: PolicyIssueReq): RestResult<String> {
        orderModifyHandler.saveOrUpdate(orderNo, OrderStatusEnum.DRAFT, JsonUtil.encode(req))
        return RestResultExt.successRestResult()
    }

    override fun delete(id: Long): RestResult<OrderResp> {
        val restResult = orderModifyHandler.operate(OrderEntity().remove(id))
        return RestResultExt.mapper(restResult)
    }
}