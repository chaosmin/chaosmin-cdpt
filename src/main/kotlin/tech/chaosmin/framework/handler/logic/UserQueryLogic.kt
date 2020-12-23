package tech.chaosmin.framework.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.dao.convert.UserMapper
import tech.chaosmin.framework.dao.dataobject.User
import tech.chaosmin.framework.domain.PageQuery
import tech.chaosmin.framework.domain.entity.UserEntity
import tech.chaosmin.framework.handler.logic.base.BaseQueryLogic
import tech.chaosmin.framework.service.DepartmentService
import tech.chaosmin.framework.service.RoleService
import tech.chaosmin.framework.service.UserService

/**
 * @author Romani min
 * @since 2020/12/17 15:47
 */
@Component
class UserQueryLogic(
    private val userService: UserService,
    private val departmentService: DepartmentService,
    private val roleService: RoleService
) : BaseQueryLogic<UserEntity, User> {

    override fun get(id: Long): UserEntity? {
        val user = userService.getById(id)
        return if (user == null) null
        else UserMapper.INSTANCE.convert2Entity(user)
    }

    override fun page(cond: PageQuery<User>): IPage<UserEntity> {
        val page = userService.page(cond.page, cond.wrapper)
        val result = page.convert(UserMapper.INSTANCE::convert2Entity)
        result.records.forEach { record ->
            val department = departmentService.getById(record.departmentId)
            record.department = department?.name
            val role = roleService.findRoles(record.id!!).firstOrNull()
            record.roleId = role?.id
            record.role = role?.name
        }
        return result
    }
}