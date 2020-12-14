package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.DepartmentDAO
import tech.chaosmin.framework.dao.dataobject.Department
import tech.chaosmin.framework.service.DepartmentService

/**
 * @author Romani min
 * @since 2020/12/9 13:51
 */
@Service
open class DepartmentServiceImpl : ServiceImpl<DepartmentDAO, Department>(), DepartmentService