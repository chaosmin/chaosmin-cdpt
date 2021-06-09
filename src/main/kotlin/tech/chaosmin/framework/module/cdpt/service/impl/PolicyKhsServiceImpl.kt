package tech.chaosmin.framework.module.cdpt.service.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.PolicyKhsDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyKhs
import tech.chaosmin.framework.module.cdpt.service.PolicyKhsService

/**
 * @author Romani min
 * @since 2021/6/7 11:10
 */
@Service
open class PolicyKhsServiceImpl : ServiceImpl<PolicyKhsDAO, PolicyKhs>(), PolicyKhsService {
    override fun listByPolicyId(policyId: Long): List<PolicyKhs> {
        val wa = Wrappers.query<PolicyKhs>().eq("policy_id", policyId)
        return baseMapper.selectList(wa)
    }
}