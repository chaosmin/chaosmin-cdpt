package tech.chaosmin.framework.module.mgmt.service.impl

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.mgmt.domain.dao.DepartmentDAO
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Department
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.DepartmentExt
import tech.chaosmin.framework.module.mgmt.service.DepartmentService

/**
 * @author Romani min
 * @since 2020/12/9 13:51
 */
@Service
open class DepartmentServiceImpl : ServiceImpl<DepartmentDAO, Department>(), DepartmentService {
    override fun getByIdExt(id: Long): DepartmentExt {
        return baseMapper.getByIdExt(id)
    }

    override fun pageExt(page: Page<DepartmentExt>, queryWrapper: Wrapper<DepartmentExt>): IPage<DepartmentExt> {
        return baseMapper.pageExt(page, queryWrapper)
    }
}