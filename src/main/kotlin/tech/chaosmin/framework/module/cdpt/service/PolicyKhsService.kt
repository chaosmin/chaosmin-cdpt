package tech.chaosmin.framework.module.cdpt.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyKhs

/**
 * @author Romani min
 * @since 2021/6/7 11:10
 */
interface PolicyKhsService : IService<PolicyKhs> {
    fun listByPolicyId(policyId: Long): List<PolicyKhs>
}