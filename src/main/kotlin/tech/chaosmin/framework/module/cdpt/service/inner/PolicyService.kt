package tech.chaosmin.framework.module.cdpt.service.inner

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.PolicyExt

/**
 * @author Romani min
 * @since 2021/1/26 15:31
 */
interface PolicyService : IService<Policy> {
    fun listExt(queryWrapper: Wrapper<PolicyExt>): List<PolicyExt>
    fun pageExt(page: Page<PolicyExt>, queryWrapper: Wrapper<PolicyExt>): IPage<PolicyExt>
}