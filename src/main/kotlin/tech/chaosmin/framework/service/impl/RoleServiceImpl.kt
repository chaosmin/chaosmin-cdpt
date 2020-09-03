package tech.chaosmin.framework.service.impl

import org.springframework.stereotype.Service
import tech.chaosmin.framework.service.RoleService

@Service
class RoleServiceImpl : RoleService {
    override fun findRoles(userId: Long): Set<Long> {
        return setOf(1L)
    }
}