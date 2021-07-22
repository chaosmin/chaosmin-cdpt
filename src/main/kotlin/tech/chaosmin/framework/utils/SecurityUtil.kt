package tech.chaosmin.framework.utils

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.definition.SystemConst
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.mgmt.domain.auth.JwtUserDetails
import tech.chaosmin.framework.module.mgmt.entity.response.UserDetailResp

object SecurityUtil {
    private val logger = LoggerFactory.getLogger(SecurityUtil::class.java)

    fun getUserId(): Long = getUserId(getAuthentication())

    fun getUsername(): String = getUsername(getAuthentication())

    fun getUserDetails(): UserDetailResp = getUserDetails(getAuthentication())

    fun getUserId(authentication: Authentication?): Long {
        if (authentication != null) {
            val principal: Any = authentication.principal
            if (principal is JwtUserDetails) {
                return principal.userId
            }
        }
        return -1
    }

    fun getUsername(authentication: Authentication?): String {
        var username: String = SystemConst.ANONYMOUS
        if (authentication != null) {
            val principal: Any = authentication.principal
            if (principal is JwtUserDetails) {
                username = principal.username
            }
        }
        return username
    }

    fun getUserDetails(authentication: Authentication?): UserDetailResp {
        if (authentication != null) {
            val principal: Any = authentication.principal
            if (principal is JwtUserDetails) {
                return UserDetailResp(principal)
            }
        }
        throw FrameworkException(ErrorCodeEnum.USER_NOT_FOUND.code)
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