package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.RoleDAO
import tech.chaosmin.framework.dao.dataobject.Role
import tech.chaosmin.framework.service.RoleService

@Service
open class RoleServiceImpl : ServiceImpl<RoleDAO, Role>(), RoleService {
    override fun findRoles(userId: Long): Set<Role> {
        return baseMapper.findRoles(userId)
    }

    @Transactional
    override fun updateRoles(userId: Long, roleIds: List<Long>): Set<Role> {
        return if (roleIds.isNotEmpty()) {
            val assigned = baseMapper.findRoles(userId).mapNotNull { it.id }
            (roleIds - assigned).run {
                if (this.isNotEmpty()) baseMapper.addRoles(userId, this)
            }
            (assigned - roleIds).run {
                if (this.isNotEmpty()) baseMapper.removeRoles(userId, this)
            }
            baseMapper.selectBatchIds(roleIds).toSet()
        } else emptySet()
    }

    @Transactional
    override fun clearRoles(userId: Long) {
        baseMapper.clearRoles(userId)
    }
}