/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.domain.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyTrace

/**
 * @author Romani min
 * @since 2021/6/7 11:10
 */
interface PolicyTraceService : IService<PolicyTrace> {
    /**
     * 关联可回溯文件信息的保单及订单关系
     * @return 影响的条数
     */
    fun linkOrderAndPolicy(orderNo: String, policyId: Long): Int
    fun listByPolicyId(policyId: Long): List<PolicyTrace>
    fun listByOrderNo(orderNo: String): List<PolicyTrace>
}