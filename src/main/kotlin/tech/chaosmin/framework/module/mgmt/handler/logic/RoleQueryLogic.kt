package tech.chaosmin.framework.module.mgmt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Role
import tech.chaosmin.framework.module.mgmt.entity.RoleEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.RoleMapper
import tech.chaosmin.framework.module.mgmt.service.AuthorityService
import tech.chaosmin.framework.module.mgmt.service.RoleService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class RoleQueryLogic(
    private val roleService: RoleService,
    private val authorityService: AuthorityService
) : BaseQueryLogic<RoleEntity, Role> {

    override fun get(id: Long): RoleEntity? {
        val role = roleService.getById(id)
        return RoleMapper.INSTANCE.convert2Entity(role)?.apply {
            val authorities = authorityService.findAuthorities(setOf(id))
            this.authorityIds = authorities.mapNotNull { it.id }.toSet()
        }
    }

    override fun page(cond: PageQuery<Role>): IPage<RoleEntity?> {
        val page = roleService.page(cond.page, cond.wrapper)
        return page.convert(RoleMapper.INSTANCE::convert2Entity)
    }
}