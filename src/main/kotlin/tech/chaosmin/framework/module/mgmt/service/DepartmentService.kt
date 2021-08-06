package tech.chaosmin.framework.module.mgmt.service

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Department
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.DepartmentExt

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface DepartmentService : IService<Department> {
    fun getByIdExt(id: Long): DepartmentExt
    fun pageExt(page: Page<DepartmentExt>, queryWrapper: Wrapper<DepartmentExt>): IPage<DepartmentExt>
}