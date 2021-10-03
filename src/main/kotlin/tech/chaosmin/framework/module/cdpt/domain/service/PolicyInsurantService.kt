/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.domain.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyInsurant

/**
 * @author Romani min
 * @since 2021/1/26 15:31
 */
interface PolicyInsurantService : IService<PolicyInsurant> {
    fun listByPolicyId(policyId: Long): List<PolicyInsurant>
}