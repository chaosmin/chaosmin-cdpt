package tech.chaosmin.framework.domain.auth

import org.springframework.security.core.GrantedAuthority

class GrantedAuthorityImpl(private var authority: String) : GrantedAuthority {
    fun setAuthority(authority: String) {
        this.authority = authority
    }

    override fun getAuthority(): String {
        return authority
    }
}