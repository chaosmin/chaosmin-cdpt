package tech.chaosmin.framework.module.cdpt.service.inner

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyHolder

/**
 * @author Romani min
 * @since 2021/1/26 15:31
 */
interface PolicyHolderService : IService<PolicyHolder> {
    fun listByPolicyId(policyId: Long): List<PolicyHolder>
}