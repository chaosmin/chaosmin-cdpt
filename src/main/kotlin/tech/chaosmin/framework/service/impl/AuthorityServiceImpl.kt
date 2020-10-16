package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.AuthorityDAO
import tech.chaosmin.framework.dao.dataobject.Authority
import tech.chaosmin.framework.service.AuthorityService

@Service
open class AuthorityServiceImpl : ServiceImpl<AuthorityDAO, Authority>(), AuthorityService {
    override fun findAuthorities(roleIds: Set<Long>): Set<Authority> {
        return if (roleIds.isEmpty()) emptySet()
        else baseMapper.findAuthorities(roleIds)
    }

    override fun addAuthorities(roleId: Long?, authorityIds: List<Long>?): Set<Authority> {
        return if (roleId != null && authorityIds != null && authorityIds.isNotEmpty()) {
            baseMapper.addAuthorities(roleId, authorityIds)
            baseMapper.selectBatchIds(authorityIds).toSet()
        } else emptySet()
    }
}