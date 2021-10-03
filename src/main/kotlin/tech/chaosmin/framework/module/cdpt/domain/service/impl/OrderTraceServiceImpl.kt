/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.domain.service.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.OrderTraceDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.OrderTrace
import tech.chaosmin.framework.module.cdpt.domain.service.OrderTraceService

/**
 * @author Romani min
 * @since 2021/6/7 11:10
 */
@Service
open class OrderTraceServiceImpl : ServiceImpl<OrderTraceDAO, OrderTrace>(), OrderTraceService {
    override fun linkOrderAndPolicy(orderNo: String, policyId: Long): Int {
        val wa = Wrappers.query<OrderTrace>().eq("order_no", orderNo)
        return baseMapper.update(OrderTrace().apply { this.policyId = policyId }, wa)
    }

    override fun listByPolicyId(policyId: Long): List<OrderTrace> {
        val wa = Wrappers.query<OrderTrace>().eq("policy_id", policyId)
        return baseMapper.selectList(wa)
    }

    override fun listByOrderNo(orderNo: String): List<OrderTrace> {
        val wa = Wrappers.query<OrderTrace>().eq("order_no", orderNo)
        return baseMapper.selectList(wa)
    }
}