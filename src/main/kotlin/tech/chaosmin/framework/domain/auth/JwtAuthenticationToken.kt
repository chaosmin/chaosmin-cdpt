package tech.chaosmin.framework.domain.auth

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

open class JwtAuthenticationToken(
    principal: Any?,
    credentials: Any?,
    authorities: Collection<GrantedAuthority> = emptyList(),
    var token: String? = null
) : UsernamePasswordAuthenticationToken(principal, credentials, authorities)