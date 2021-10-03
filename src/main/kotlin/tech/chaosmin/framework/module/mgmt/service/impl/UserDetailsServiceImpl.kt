package tech.chaosmin.framework.module.mgmt.service.impl

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.UserStatusEnum
import tech.chaosmin.framework.exception.PermissionException
import tech.chaosmin.framework.module.mgmt.domain.auth.GrantedAuthorityImpl
import tech.chaosmin.framework.module.mgmt.domain.auth.JwtUserDetails
import tech.chaosmin.framework.module.mgmt.service.UserService

@Service
class UserDetailsServiceImpl(private val userService: UserService) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userService.findByLoginName(username)?.run {
            val roles = this.roles
            if (roles.isNullOrEmpty()) {
                throw PermissionException(ErrorCodeEnum.NO_PERMISSION.code)
            }
            val roleCodes = roles.mapNotNull { it.code }
            val authorities = roles.flatMap { role -> role.authorities!!.map { GrantedAuthorityImpl(it.authority) } }
                .filterNot { it.authority.startsWith("null") }.distinctBy { it.authority }

            val accountNonExpired = this.status != null && this.status == UserStatusEnum.VALID.getCode()
            val credentialsNonExpired = true
            val accountNonLocked = true
            JwtUserDetails(
                this.id!!, username, this.password!!, this.departmentId,
                roleCodes, authorities,
                accountNonExpired && credentialsNonExpired && accountNonLocked,
                accountNonExpired, credentialsNonExpired, accountNonLocked
            )
        } ?: throw UsernameNotFoundException(ErrorCodeEnum.USER_NOT_FOUND.code)
    }
}