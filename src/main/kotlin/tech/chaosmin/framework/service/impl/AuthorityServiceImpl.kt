package tech.chaosmin.framework.service.impl

import org.springframework.stereotype.Service
import tech.chaosmin.framework.service.AuthorityService
import java.util.*


@Service
class AuthorityServiceImpl : AuthorityService {
    override fun findAuthorities(roleIds: Set<Long>): Set<String> {
        val permissions: MutableSet<String> = HashSet()
        permissions.add("sys:user:view")
        permissions.add("sys:user:add")
        permissions.add("sys:user:edit")
        permissions.add("sys:user:delete")
        return permissions
    }

}