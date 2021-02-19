package tech.chaosmin.framework.module.mgmt.service.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.mgmt.domain.dao.AuthorityDAO
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Authority
import tech.chaosmin.framework.module.mgmt.service.AuthorityService

@Service
open class AuthorityServiceImpl : ServiceImpl<AuthorityDAO, Authority>(), AuthorityService {
    override fun findAuthorities(roleId: Long): Set<Authority> {
        return baseMapper.fetchAuthorityByRoleId(roleId)
    }

    override fun findAuthorities(roleIds: Set<Long>): Set<Authority> {
        return if (roleIds.isEmpty()) emptySet()
        else baseMapper.fetchAuthorityByRoleIds(roleIds)
    }

    @Cacheable(value = ["authorities"], key = "#authority", unless = "#result == null")
    override fun findAuthorities(authority: String): Authority? {
        val method = authority.split(" ")[0]
        val url = authority.split(" ")[1]
        return baseMapper.selectOne(Wrappers.query(Authority(method, url)))
    }

    override fun updateAuthorities(roleId: Long, authorityIds: List<Long>): Set<Authority> {
        return if (authorityIds.isNotEmpty()) {
            val assigned = baseMapper.fetchAuthorityByRoleId(roleId).mapNotNull { it.id }
            (authorityIds - assigned).run {
                if (this.isNotEmpty()) baseMapper.addAuthorities(roleId, this)
            }
            (assigned - authorityIds).run {
                if (this.isNotEmpty()) baseMapper.removeAuthorities(roleId, this)
            }
            baseMapper.selectBatchIds(authorityIds).toSet()
        } else emptySet()
    }

    override fun clearAuthorities(roleId: Long) {
        baseMapper.clearAuthorities(roleId)
    }
}