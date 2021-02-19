package tech.chaosmin.framework.module.mgmt.domain.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Param
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Authority

interface AuthorityDAO : BaseMapper<Authority> {
    fun fetchAuthorityByRoleId(@Param("id") roleId: Long): Set<Authority>

    fun fetchAuthorityByRoleIds(@Param("roleIds") roleIds: Set<Long>): Set<Authority>

    fun addAuthorities(@Param("roleId") roleId: Long, @Param("authorityIds") authorityIds: List<Long>)

    fun removeAuthorities(@Param("roleId") roleId: Long, @Param("authorityIds") authorityIds: List<Long>)

    fun clearAuthorities(@Param("roleId") roleId: Long)
}