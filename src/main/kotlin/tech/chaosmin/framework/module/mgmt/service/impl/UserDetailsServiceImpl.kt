package tech.chaosmin.framework.module.mgmt.service.impl

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.mgmt.service.StoreService

@Service
class UserDetailsServiceImpl(private val storeService: StoreService) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return storeService.fetchJwtUserDetail(username)
    }
}