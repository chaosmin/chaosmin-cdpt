package tech.chaosmin.framework.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.dao.convert.DepartmentConvert
import tech.chaosmin.framework.dao.dataobject.Department
import tech.chaosmin.framework.domain.PageQuery
import tech.chaosmin.framework.domain.response.DepartmentResp
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
    fun run(cond: PageQuery<Department>): IPage<DepartmentResp> {
        val page = departmentService.page(cond.page, cond.wrapper)
        val result = page.convert(DepartmentConvert.INSTANCE::convert2Resp)
        result.records.forEach { it.numberOfPeople = userService.countByDepartmentId(it.id!!) }
        return result
    }
}