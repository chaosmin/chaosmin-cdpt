package tech.chaosmin.framework.module.mgmt.domain.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

class JwtUserDetails(
    var userId: Long,
    username: String,
    password: String,
    var payType: String?,
    var roles: List<String>,
    enabled: Boolean = true,
    accountNonExpired: Boolean = true,
    credentialsNonExpired: Boolean = true,
    accountNonLocked: Boolean = true,
    authorities: Collection<GrantedAuthority> = emptyList()
) : User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities), UserDetails