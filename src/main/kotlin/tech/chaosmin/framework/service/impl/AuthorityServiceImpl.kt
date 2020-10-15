package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.AuthorityDAO
import tech.chaosmin.framework.dao.dataobject.Authority
import tech.chaosmin.framework.service.AuthorityService
import java.util.*

@Service
open class AuthorityServiceImpl : ServiceImpl<AuthorityDAO, Authority>(), AuthorityService {
    override fun findAuthorities(roleIds: Set<Long>): Set<String> {
        val permissions: MutableSet<String> = HashSet()
        permissions.add("sys:user:view")
        permissions.add("sys:user:add")
        permissions.add("sys:user:edit")
        permissions.add("sys:user:delete")
        return permissions
    }
}