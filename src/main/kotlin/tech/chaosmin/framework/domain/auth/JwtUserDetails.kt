package tech.chaosmin.framework.domain.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

class JwtUserDetails(
    username: String,
    password: String,
    var department: Long?,
    var roles: List<String>,
    authorities: Collection<GrantedAuthority> = emptyList(),
    enabled: Boolean = true,
    accountNonExpired: Boolean = true,
    credentialsNonExpired: Boolean = true,
    accountNonLocked: Boolean = true
) : User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities),
    UserDetails