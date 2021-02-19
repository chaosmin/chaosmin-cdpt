package tech.chaosmin.framework.module.mgmt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.mgmt.entity.UserEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.UserMapper
import tech.chaosmin.framework.module.mgmt.service.RoleService
import tech.chaosmin.framework.module.mgmt.service.UserService

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
    override fun validation(arg: UserEntity, result: RestResult<UserEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
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
}