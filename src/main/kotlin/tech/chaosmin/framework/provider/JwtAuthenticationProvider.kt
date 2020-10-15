package tech.chaosmin.framework.provider

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class JwtAuthenticationProvider() : DaoAuthenticationProvider() {

    constructor(userDetailsService: UserDetailsService, passwordEncoder: BCryptPasswordEncoder) : this() {
        setUserDetailsService(userDetailsService)
        this.passwordEncoder = passwordEncoder
    }

    override fun authenticate(authentication: Authentication): Authentication {
        // 可以在此处覆写整个登录认证逻辑
        return super.authenticate(authentication)
    }

    override fun additionalAuthenticationChecks(
        userDetails: UserDetails,
        authentication: UsernamePasswordAuthenticationToken
    ) {
        // 可以在此处覆写密码验证逻辑
        super.additionalAuthenticationChecks(userDetails, authentication)
    }
}
