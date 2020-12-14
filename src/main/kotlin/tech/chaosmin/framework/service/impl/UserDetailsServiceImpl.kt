package tech.chaosmin.framework.service.impl

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import tech.chaosmin.framework.domain.auth.GrantedAuthorityImpl
import tech.chaosmin.framework.domain.auth.JwtUserDetails
import tech.chaosmin.framework.domain.enums.DepartmentStatusEnum
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.domain.enums.UserStatusEnum
import tech.chaosmin.framework.service.AuthorityService
import tech.chaosmin.framework.service.DepartmentService
import tech.chaosmin.framework.service.RoleService
import tech.chaosmin.framework.service.UserService


@Service
class UserDetailsServiceImpl(
    private val departmentService: DepartmentService,
    private val userService: UserService,
    private val roleService: RoleService,
    private val authorityService: AuthorityService
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userService.findByLoginName(username)?.run {
            // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
            val roleIds = roleService.findRoles(this.id!!).mapNotNull { it.id }.toSet()
            val permissions = authorityService.findAuthorities(roleIds).map { it.authority }
            val accountNonExpired = this.status != null && this.status == UserStatusEnum.VALID.code
            val credentialsNonExpired = true
            val accountNonLocked = if (this.departmentId != null) {
                val department = departmentService.getById(this.departmentId)
                department?.status != null && department.status == DepartmentStatusEnum.VALID.code
            } else true
            JwtUserDetails(
                username,
                this.password!!,
                permissions.map { GrantedAuthorityImpl(it) },
                accountNonExpired && credentialsNonExpired && accountNonLocked,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked
            )
        } ?: throw UsernameNotFoundException(ErrorCodeEnum.USER_NOT_FOUND.code)
    }
}