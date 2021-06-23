package tech.chaosmin.framework.module.mgmt.handler.logic

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Role
import tech.chaosmin.framework.module.mgmt.entity.RoleEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.RoleMapper
import tech.chaosmin.framework.module.mgmt.service.AuthorityService
import tech.chaosmin.framework.module.mgmt.service.RoleService
import tech.chaosmin.framework.utils.SecurityUtil

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class RoleQueryLogic(
    private val roleService: RoleService,
    private val authorityService: AuthorityService
) : BaseQueryLogic<RoleEntity, Role> {

    fun fetchAuthority(id: Long): List<Long> {
        val authorities = authorityService.findAuthorities(id)
        return authorities.mapNotNull { it.id }
    }

    override fun get(id: Long): RoleEntity? {
        val role = roleService.getById(id)
        return RoleMapper.INSTANCE.convert2Entity(role)
    }

    override fun page(cond: PageQuery<Role>): IPage<RoleEntity?> {
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