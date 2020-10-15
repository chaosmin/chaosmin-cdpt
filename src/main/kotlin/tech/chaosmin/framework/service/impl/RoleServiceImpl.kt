package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.RoleDAO
import tech.chaosmin.framework.dao.dataobject.Role
import tech.chaosmin.framework.service.RoleService

@Service
open class RoleServiceImpl : ServiceImpl<RoleDAO, Role>(), RoleService {
    override fun findRoles(userId: Long): Set<Long> {
        return setOf(1L)
    }
}