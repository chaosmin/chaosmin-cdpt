package tech.chaosmin.framework.module.mgmt.domain.auth

import org.springframework.core.NamedInheritableThreadLocal
import org.springframework.security.core.Authentication

/**
 * 登录用户信息ContextHolder
 */
object AuthContextHolder {
    private val ctxHolder = NamedInheritableThreadLocal<Authentication>("AuthenticationHolder")
    fun getAuthentication(): Authentication? = ctxHolder.get()
    fun setAuthentication(authentication: Authentication) = ctxHolder.set(authentication)
    fun clean() = ctxHolder.remove()
}