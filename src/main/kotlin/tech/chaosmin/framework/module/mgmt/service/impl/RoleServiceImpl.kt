package tech.chaosmin.framework.module.mgmt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.mgmt.domain.dao.RoleDAO
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Role
import tech.chaosmin.framework.module.mgmt.service.RoleService

@Service
open class RoleServiceImpl : ServiceImpl<RoleDAO, Role>(), RoleService {
    override fun findRoles(userId: Long): Set<Role> {
        return baseMapper.fetchRoleByUserId(userId)
    }

    override fun updateRoles(userId: Long, roleIds: List<Long>): Set<Role> {
        return if (roleIds.isNotEmpty()) {
            val assigned = baseMapper.fetchRoleByUserId(userId).mapNotNull { it.id }
            (roleIds - assigned).run {
                if (this.isNotEmpty()) baseMapper.addRoles(userId, this)
            }
            (assigned - roleIds).run {
                if (this.isNotEmpty()) baseMapper.removeRoles(userId, this)
            }
            baseMapper.selectBatchIds(roleIds).toSet()
        } else emptySet()
    }

    override fun clearRoles(userId: Long) {
        baseMapper.clearRoles(userId)
    }
}