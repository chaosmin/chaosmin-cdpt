package tech.chaosmin.framework.module.cdpt.service.inner.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.PolicyKhsDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyKhs
import tech.chaosmin.framework.module.cdpt.service.inner.PolicyKhsService

/**
 * @author Romani min
 * @since 2021/6/7 11:10
 */
@Service
open class PolicyKhsServiceImpl : ServiceImpl<PolicyKhsDAO, PolicyKhs>(), PolicyKhsService {
    override fun linkOrderAndPolicy(orderNo: String, policyId: Long): Int {
        val wa = Wrappers.query<PolicyKhs>().eq("order_no", orderNo)
        return baseMapper.update(PolicyKhs().apply { this.policyId = policyId }, wa)
    }

    override fun listByPolicyId(policyId: Long): List<PolicyKhs> {
        val wa = Wrappers.query<PolicyKhs>().eq("policy_id", policyId)
        return baseMapper.selectList(wa)
    }

    override fun listByOrderNo(orderNo: String): List<PolicyKhs> {
        val wa = Wrappers.query<PolicyKhs>().eq("order_no", orderNo)
        return baseMapper.selectList(wa)
    }
}