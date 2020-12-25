package tech.chaosmin.framework.dao

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Constants
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Param
import tech.chaosmin.framework.dao.dataobject.Department
import tech.chaosmin.framework.dao.dataobject.ext.DepartmentExt

/**
 * @author Romani min
 * @since 2020/12/9 13:49
 */
interface DepartmentDAO : BaseMapper<Department> {
    fun pageExt(
        page: Page<DepartmentExt>,
        @Param(Constants.WRAPPER) queryWrapper: Wrapper<DepartmentExt>
    ): IPage<DepartmentExt>
}