package tech.chaosmin.framework.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.dao.dataobject.Authority

open interface AuthorityService : IService<Authority> {
    fun findAuthorities(roleIds: Set<Long>): Set<Authority>
    fun addAuthorities(roleId: Long?, authorityIds: List<Long>?): Set<Authority>
}