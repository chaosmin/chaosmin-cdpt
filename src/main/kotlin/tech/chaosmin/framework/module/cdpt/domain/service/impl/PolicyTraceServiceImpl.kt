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
import tech.chaosmin.framework.module.cdpt.domain.dao.PolicyTraceDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyTrace
import tech.chaosmin.framework.module.cdpt.domain.service.PolicyTraceService

/**
 * @author Romani min
 * @since 2021/6/7 11:10
 */
@Service
open class PolicyTraceServiceImpl : ServiceImpl<PolicyTraceDAO, PolicyTrace>(), PolicyTraceService {
    override fun linkOrderAndPolicy(orderNo: String, policyId: Long): Int {
        val wa = Wrappers.query<PolicyTrace>().eq("order_no", orderNo)
        return baseMapper.update(PolicyTrace().apply { this.policyId = policyId }, wa)
    }

    override fun listByPolicyId(policyId: Long): List<PolicyTrace> {
        val wa = Wrappers.query<PolicyTrace>().eq("policy_id", policyId)
        return baseMapper.selectList(wa)
    }

    override fun listByOrderNo(orderNo: String): List<PolicyTrace> {
        val wa = Wrappers.query<PolicyTrace>().eq("order_no", orderNo)
        return baseMapper.selectList(wa)
    }
}