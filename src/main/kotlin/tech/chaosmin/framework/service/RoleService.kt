package tech.chaosmin.framework.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.dao.dataobject.Role

interface RoleService : IService<Role> {
    fun findRoles(userId: Long): Set<Role>
    fun updateRoles(userId: Long, roleIds: List<Long>): Set<Role>
    fun clearRoles(userId: Long)
}