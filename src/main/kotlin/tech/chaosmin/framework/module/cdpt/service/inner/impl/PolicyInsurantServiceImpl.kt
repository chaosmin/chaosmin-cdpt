package tech.chaosmin.framework.module.cdpt.service.inner.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.PolicyInsurantDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyInsurant
import tech.chaosmin.framework.module.cdpt.service.inner.PolicyInsurantService

/**
 * @author Romani min
 * @since 2021/1/26 15:31
 */
@Service
open class PolicyInsurantServiceImpl : ServiceImpl<PolicyInsurantDAO, PolicyInsurant>(), PolicyInsurantService {
    override fun listByPolicyId(policyId: Long): List<PolicyInsurant> {
        val wa = Wrappers.query<PolicyInsurant>().eq("policy_id", policyId)
        return baseMapper.selectList(wa)
    }
}