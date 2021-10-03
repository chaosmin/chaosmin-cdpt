/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * RoleInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.mgmt.logic.interrogator

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Role
import tech.chaosmin.framework.module.mgmt.entity.RoleEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.RoleMapper
import tech.chaosmin.framework.module.mgmt.service.AuthorityService
import tech.chaosmin.framework.module.mgmt.service.RoleService
import tech.chaosmin.framework.utils.SecurityUtil

/**
 * @author Romani min
 * @since 2021/9/4 23:47
 */
@Component
class RoleInterrogator(
    private val roleService: RoleService,
    private val authorityService: AuthorityService
) : Interrogator<RoleEntity, Role> {

    fun fetchAuthority(id: Long): List<Long> {
        val authorities = authorityService.findAuthorities(id)
        return authorities.mapNotNull { it.id }
    }

    override fun getOne(id: Long): RoleEntity? {
        val role = roleService.getById(id)
        return RoleMapper.INSTANCE.convert2Entity(role)
    }

    override fun page(cond: PageQuery<Role>): IPage<RoleEntity> {
        var queryWrapper = cond.wrapper
        if (!SecurityUtil.getUserDetails().isAdmin) {
            val roles = SecurityUtil.getUserDetails().roles
            val priority = roleService.list(QueryWrapper<Role?>().`in`("code", roles))
                .minBy { it.priority ?: Int.MAX_VALUE }?.priority
            queryWrapper = queryWrapper.gt("priority", priority)
        }
        val page = roleService.page(cond.page, queryWrapper)
        return page.convert(RoleMapper.INSTANCE::convert2Entity)
    }
}