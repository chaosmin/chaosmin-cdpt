package tech.chaosmin.framework.module.mgmt.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Authority

interface AuthorityService : IService<Authority> {
    fun findAuthorities(roleIds: Set<Long>): Set<Authority>
    fun findAuthorities(authority: String): Authority?
    fun updateAuthorities(roleId: Long, authorityIds: List<Long>): Set<Authority>
    fun clearAuthorities(roleId: Long)
}