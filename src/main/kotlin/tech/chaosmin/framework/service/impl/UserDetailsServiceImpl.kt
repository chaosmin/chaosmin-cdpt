package tech.chaosmin.framework.service.impl

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import tech.chaosmin.framework.domain.auth.GrantedAuthorityImpl
import tech.chaosmin.framework.domain.auth.JwtUserDetails
import tech.chaosmin.framework.service.AuthorityService
import tech.chaosmin.framework.service.RoleService
import tech.chaosmin.framework.service.UserService
import javax.annotation.Resource


@Service
class UserDetailsServiceImpl : UserDetailsService {
    @Resource
    lateinit var userService: UserService

    @Resource
    lateinit var roleService: RoleService

    @Resource
    lateinit var authorityService: AuthorityService

    override fun loadUserByUsername(username: String): UserDetails? {
        return userService.findByLoginName(username)?.run {
            // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
            val permissions = authorityService.findAuthorities(roleService.findRoles(this.id!!))
            JwtUserDetails(username, this.password!!, permissions.map { GrantedAuthorityImpl(it) })
        } ?: throw UsernameNotFoundException("User not found in system.")
    }
}