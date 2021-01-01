package tech.chaosmin.framework.module.mgmt.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Role

interface RoleService : IService<Role> {
    fun findRoles(userId: Long): Set<Role>
    fun updateRoles(userId: Long, roleIds: List<Long>): Set<Role>
    fun clearRoles(userId: Long)
}