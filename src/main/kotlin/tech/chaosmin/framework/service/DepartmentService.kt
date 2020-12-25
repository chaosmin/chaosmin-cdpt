package tech.chaosmin.framework.service

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.dao.dataobject.Department
import tech.chaosmin.framework.dao.dataobject.ext.DepartmentExt

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface DepartmentService : IService<Department> {
    fun pageExt(page: Page<DepartmentExt>, queryWrapper: Wrapper<DepartmentExt>): IPage<DepartmentExt>
}