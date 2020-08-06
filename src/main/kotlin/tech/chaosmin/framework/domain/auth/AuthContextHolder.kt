package tech.chaosmin.framework.domain.auth

import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.core.NamedInheritableThreadLocal

/**
 * 登录用户信息ContextHolder
 */
object AuthContextHolder {
    private val ctxHolder = NamedInheritableThreadLocal<Authentication>("AuthenticationHolder")
    fun getAuthentication(): Authentication? = ctxHolder.get()
    fun setAuthentication(authentication: Authentication) = ctxHolder.set(authentication)
    fun clean() = ctxHolder.remove()
}