package tech.chaosmin.framework.module.mgmt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.base.enums.UserStatusEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.mgmt.domain.dataobject.User
import tech.chaosmin.framework.module.mgmt.entity.UserEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.UserMapper
import tech.chaosmin.framework.module.mgmt.service.RoleService
import tech.chaosmin.framework.module.mgmt.service.UserService
import javax.annotation.PostConstruct

/**
 * @author Romani min
 * @since 2020/12/23 15:13
 */
@Component
open class ModifyUserHandler(
    private val passwordEncoder: BCryptPasswordEncoder,
    private val userService: UserService,
    private val roleService: RoleService
) : AbstractTemplateOperate<UserEntity, UserEntity>() {
    private val logger = LoggerFactory.getLogger(ModifyUserHandler::class.java)

    override fun validation(arg: UserEntity, result: RestResult<UserEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    @PostConstruct
    fun resetAdminPassword() {
        val np = "uGd10qd9DHW2DewC"
        var admin = userService.list(Wrappers.query<User?>().eq("login_name", "admin")).firstOrNull()
        if (admin == null) {
            logger.error("创建超级管理员账号 >>> password: $np")
            admin = User().apply {
                this.username = "系统最高管理员"
                this.loginName = "admin"
                this.password = passwordEncoder.encode(np)
                this.status = UserStatusEnum.VALID.getCode()
            }
            // 赋予超级管理员权限
            userService.save(admin)
            roleService.updateRoles(admin.id!!, listOf(1L))
        } else {
            // 否则更新密码
            logger.error("更新超级管理员账号 >>> password: $np")
            admin.password = passwordEncoder.encode(np)
            userService.updateById(admin)
        }
    }

    @Transactional
    override fun processor(arg: UserEntity, result: RestResult<UserEntity>): RestResult<UserEntity> {
        val user = UserMapper.INSTANCE.convert2DO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> {
                user.password = passwordEncoder.encode(arg.password)
                if (userService.save(user)) {
                    setUserRoles(user.id, arg.roleIds?.toSet())
                }
            }
            ModifyTypeEnum.UPDATE -> {
                if (!arg.password.isNullOrBlank()) {
                    user.password = passwordEncoder.encode(arg.password)
                }
                if (userService.updateById(user)) {
                    setUserRoles(user.id, arg.roleIds?.toSet())
                }
            }
            ModifyTypeEnum.REMOVE -> {
                userService.remove(Wrappers.query(user))
                user.id?.run { roleService.clearRoles(this) }
            }
        }
        return result.success(arg)
    }

    private fun setUserRoles(userId: Long?, roleIds: Set<Long?>?) {
        if (userId != null && !roleIds.isNullOrEmpty()) {
            roleService.updateRoles(userId, roleIds.filterNotNull())
        }
    }

    fun isSamePassword(u: UserEntity, n: String?): Boolean {
        return u.password == passwordEncoder.encode(n)
    }
}