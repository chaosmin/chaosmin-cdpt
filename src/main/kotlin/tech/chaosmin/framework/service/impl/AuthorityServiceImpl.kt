package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.AuthorityDAO
import tech.chaosmin.framework.dao.dataobject.Authority
import tech.chaosmin.framework.service.AuthorityService

@Service
open class AuthorityServiceImpl : ServiceImpl<AuthorityDAO, Authority>(), AuthorityService {
    override fun findAuthorities(roleIds: Set<Long>): Set<Authority> {
        return if (roleIds.isEmpty()) emptySet()
        else baseMapper.findAuthorities(roleIds)
    }

    @Transactional
    override fun updateAuthorities(roleId: Long?, authorityIds: List<Long>?): Set<Authority> {
        return if (roleId != null && authorityIds != null && authorityIds.isNotEmpty()) {
            val assigned = baseMapper.findAuthorities(setOf(roleId)).mapNotNull { it.id }
            (authorityIds - assigned).run {
                if (this.isNotEmpty()) baseMapper.addAuthorities(roleId, this)
            }
            (assigned - authorityIds).run {
                if (this.isNotEmpty()) baseMapper.removeAuthorities(roleId, this)
            }
            baseMapper.selectBatchIds(authorityIds).toSet()
        } else emptySet()
    }

    @Transactional
    override fun clearAuthorities(roleId: Long) {
        baseMapper.clearAuthorities(roleId)
    }
}