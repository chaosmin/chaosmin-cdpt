package tech.chaosmin.framework.domain.auth

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken(
    principal: Any?,
    credentials: Any?,
    authorities: Collection<GrantedAuthority> = emptyList()
) : UsernamePasswordAuthenticationToken(principal, credentials, authorities) {
    var token: String? = null

    constructor(
        principal: Any?,
        credentials: Any?,
        authorities: Collection<GrantedAuthority> = emptyList(),
        token: String?
    ) : this(
        principal,
        credentials,
        authorities
    ) {
        this.token = token
    }
}