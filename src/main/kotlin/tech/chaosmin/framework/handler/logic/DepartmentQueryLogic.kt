package tech.chaosmin.framework.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.dao.convert.DepartmentMapper
import tech.chaosmin.framework.dao.dataobject.ext.DepartmentExt
import tech.chaosmin.framework.domain.PageQuery
import tech.chaosmin.framework.domain.entity.DepartmentEntity
import tech.chaosmin.framework.handler.logic.base.BaseQueryLogic
import tech.chaosmin.framework.service.DepartmentService
import tech.chaosmin.framework.service.UserService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class DepartmentQueryLogic(
    private val departmentService: DepartmentService,
    private val userService: UserService
) : BaseQueryLogic<DepartmentEntity, DepartmentExt> {

    override fun get(id: Long): DepartmentEntity? {
        val department = departmentService.getById(id)
        return if (department == null) null
        else DepartmentMapper.INSTANCE.convert2Entity(department)
    }

    override fun page(cond: PageQuery<DepartmentExt>): IPage<DepartmentEntity> {
        val page = departmentService.pageExt(cond.page, cond.wrapper)
        return page.convert(DepartmentMapper.INSTANCE::convert2Entity)
    }
}