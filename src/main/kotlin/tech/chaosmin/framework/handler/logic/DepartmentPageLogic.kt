package tech.chaosmin.framework.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.dao.convert.DepartmentConvert
import tech.chaosmin.framework.dao.dataobject.Department
import tech.chaosmin.framework.domain.PageQueryDTO
import tech.chaosmin.framework.domain.response.share.DepartmentShareResponseDTO
import tech.chaosmin.framework.service.DepartmentService
import tech.chaosmin.framework.service.UserService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class DepartmentPageLogic(
    private val departmentService: DepartmentService,
    private val userService: UserService
) {
    fun run(cond: PageQueryDTO<Department>): IPage<DepartmentShareResponseDTO> {
        val page = departmentService.page(cond.page, cond.wrapper)
        val result = page.convert(DepartmentConvert.INSTANCE::convertToShareResponse)
        result.records.forEach { it.numberOfPeople = userService.countByDepartmentId(it.id!!) }
        return result
    }
}