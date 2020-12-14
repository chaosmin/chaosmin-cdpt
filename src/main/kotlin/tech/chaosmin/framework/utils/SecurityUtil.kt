package tech.chaosmin.framework.utils

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import tech.chaosmin.framework.domain.const.SystemConst

object SecurityUtil {
    private val logger = LoggerFactory.getLogger(SecurityUtil::class.java)

    fun getUsername(): String {
        return getUsername(getAuthentication())
    }

    fun getUsername(authentication: Authentication?): String {
        var username: String = SystemConst.ANONYMOUS
        if (authentication != null) {
            val principal: Any = authentication.principal
            if (principal is UserDetails) {
                username = principal.username
            }
        }
        return username
    }

    fun setAuthentication(authentication: Authentication) {
        SecurityContextHolder.getContext().authentication = authentication
    }

    fun getAuthentication(): Authentication? {
        return if (SecurityContextHolder.getContext() == null) {
            logger.warn("SecurityContextHolder.getContext() is null.")
            null
        } else SecurityContextHolder.getContext().authentication
    }

    fun clean() {
        logger.debug("Clean SecurityContextHolder.getContext()")
        SecurityContextHolder.clearContext()
    }
}