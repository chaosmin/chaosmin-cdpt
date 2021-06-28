package tech.chaosmin.framework.module.cdpt.service.inner

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyKhs

/**
 * @author Romani min
 * @since 2021/6/7 11:10
 */
interface PolicyKhsService : IService<PolicyKhs> {
    /**
     * 关联可回溯文件信息的保单及订单关系
     * @return 影响的条数
     */
    fun linkOrderAndPolicy(orderNo: String, policyId: Long): Int
    fun listByPolicyId(policyId: Long): List<PolicyKhs>
    fun listByOrderNo(orderNo: String): List<PolicyKhs>
}