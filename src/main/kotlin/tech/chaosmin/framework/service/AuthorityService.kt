package tech.chaosmin.framework.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.dao.dataobject.Authority

open interface AuthorityService : IService<Authority> {
    fun findAuthorities(roleIds: Set<Long>): Set<String>
}