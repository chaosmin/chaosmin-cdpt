package tech.chaosmin.framework.module.mgmt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.DepartmentExt
import tech.chaosmin.framework.module.mgmt.entity.DepartmentEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.DepartmentMapper
import tech.chaosmin.framework.module.mgmt.service.DepartmentService
import tech.chaosmin.framework.utils.SecurityUtil

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class DepartmentQueryLogic(private val departmentService: DepartmentService) : BaseQueryLogic<DepartmentEntity, DepartmentExt> {

    override fun get(id: Long): DepartmentEntity? {
        val department = departmentService.getById(id)
        return DepartmentMapper.INSTANCE.convert2Entity(department)
    }

    override fun page(cond: PageQuery<DepartmentExt>): IPage<DepartmentEntity?> {
        var queryWrapper = cond.wrapper
        if (!SecurityUtil.getUserDetails().isAdmin) {
            queryWrapper = queryWrapper.eq("id", SecurityUtil.getUserDetails().departmentId)
        }
        val page = departmentService.pageExt(cond.page, queryWrapper)
        return page.convert(DepartmentMapper.INSTANCE::convert2Entity)
    }
}