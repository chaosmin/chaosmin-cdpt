package tech.chaosmin.framework.module.mgmt.domain.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Param
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Role

interface RoleDAO : BaseMapper<Role> {
    fun fetchRoleByUserId(@Param("userId") userId: Long): Set<Role>

    fun addRoles(@Param("userId") userId: Long, @Param("roleIds") roleIds: List<Long>)

    fun removeRoles(@Param("userId") userId: Long, @Param("roleIds") roleIds: List<Long>)

    fun clearRoles(@Param("userId") userId: Long)
}