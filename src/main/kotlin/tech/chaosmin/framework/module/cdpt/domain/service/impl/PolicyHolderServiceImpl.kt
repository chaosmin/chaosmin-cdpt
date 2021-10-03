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
import tech.chaosmin.framework.module.cdpt.domain.dao.PolicyHolderDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyHolder
import tech.chaosmin.framework.module.cdpt.domain.service.PolicyHolderService

/**
 * @author Romani min
 * @since 2021/1/26 15:31
 */
@Service
open class PolicyHolderServiceImpl : ServiceImpl<PolicyHolderDAO, PolicyHolder>(), PolicyHolderService {
    override fun listByPolicyId(policyId: Long): List<PolicyHolder> {
        val wa = Wrappers.query<PolicyHolder>().eq("policy_id", policyId)
        return baseMapper.selectList(wa)
    }
}