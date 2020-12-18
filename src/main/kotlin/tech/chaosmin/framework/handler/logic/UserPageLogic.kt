package tech.chaosmin.framework.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.dao.convert.UserConvert
import tech.chaosmin.framework.dao.dataobject.User
import tech.chaosmin.framework.domain.PageQueryDTO
import tech.chaosmin.framework.domain.response.share.UserShareResponseDTO
import tech.chaosmin.framework.service.DepartmentService
import tech.chaosmin.framework.service.RoleService
import tech.chaosmin.framework.service.UserService

/**
 * @author Romani min
 * @since 2020/12/17 15:47
 */
@Component
class UserPageLogic(
    private val userService: UserService,
    private val departmentService: DepartmentService,
    private val roleService: RoleService
) {
    fun run(cond: PageQueryDTO<User>): IPage<UserShareResponseDTO> {
        val page = userService.page(cond.page, cond.wrapper)
        val result = page.convert(UserConvert.INSTANCE::convertToShareResponse)
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