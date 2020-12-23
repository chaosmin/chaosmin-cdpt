package tech.chaosmin.framework.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.dao.convert.RoleMapper
import tech.chaosmin.framework.dao.dataobject.Role
import tech.chaosmin.framework.domain.PageQuery
import tech.chaosmin.framework.domain.entity.RoleEntity
import tech.chaosmin.framework.handler.logic.base.BaseQueryLogic
import tech.chaosmin.framework.service.RoleService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class RoleQueryLogic(private val roleService: RoleService) : BaseQueryLogic<RoleEntity, Role> {

    override fun get(id: Long): RoleEntity? {
        val role = roleService.getById(id)
        return if (role == null) null
        else RoleMapper.INSTANCE.convert2Entity(role)
    }

    override fun page(cond: PageQuery<Role>): IPage<RoleEntity> {
        val page = roleService.page(cond.page, cond.wrapper)
        return page.convert(RoleMapper.INSTANCE::convert2Entity)
    }
}