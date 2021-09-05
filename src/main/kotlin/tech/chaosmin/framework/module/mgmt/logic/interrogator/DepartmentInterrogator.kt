/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * DepartmentInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.mgmt.logic.interrogator

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.DepartmentExt
import tech.chaosmin.framework.module.mgmt.entity.DepartmentEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.DepartmentMapper
import tech.chaosmin.framework.module.mgmt.service.DepartmentService
import tech.chaosmin.framework.utils.SecurityUtil

/**
 * @author Romani min
 * @since 2021/9/4 23:47
 */
@Component
class DepartmentInterrogator(private val departmentService: DepartmentService) : Interrogator<DepartmentEntity, DepartmentExt> {
    override fun getOne(id: Long): DepartmentEntity? {
        val department = departmentService.getByIdExt(id)
        return DepartmentMapper.INSTANCE.convert2Entity(department)
    }

    override fun page(cond: PageQuery<DepartmentExt>): IPage<DepartmentEntity> {
        var queryWrapper = cond.wrapper
        if (!SecurityUtil.getUserDetails().isAdmin) {
            queryWrapper = queryWrapper.eq("id", SecurityUtil.getUserDetails().departmentId)
        }
        val page = departmentService.pageExt(cond.page, queryWrapper)
        return page.convert(DepartmentMapper.INSTANCE::convert2Entity)
    }
}