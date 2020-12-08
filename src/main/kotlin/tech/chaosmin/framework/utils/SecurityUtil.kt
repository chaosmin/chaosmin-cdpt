package tech.chaosmin.framework.utils

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

object SecurityUtil {
    fun getUsername(): String? {
        return getUsername(getAuthentication())
    }

    fun getUsername(authentication: Authentication?): String? {
        var username: String? = null
        if (authentication != null) {
            val principal: Any = authentication.principal
            if (principal is UserDetails) {
                username = principal.username
            }
        }
        return username
    }

    fun getAuthentication(): Authentication? {
        return if (SecurityContextHolder.getContext() == null) null
        else SecurityContextHolder.getContext().authentication
    }

    fun clean() {
        SecurityContextHolder.clearContext()
    }
}