package tech.chaosmin.framework.module.mgmt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.definition.SystemConst.APPLICATION_NAME
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.mgmt.entity.UserEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.UserMapper
import tech.chaosmin.framework.module.mgmt.service.RoleService
import tech.chaosmin.framework.module.mgmt.service.UserService
import tech.chaosmin.framework.utils.SecurityUtil
import javax.annotation.Resource

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

    @Resource
    lateinit var redisTemplate: RedisTemplate<String, UserEntity>

    override fun validation(arg: UserEntity, result: RestResult<UserEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    @Transactional
    override fun processor(arg: UserEntity, result: RestResult<UserEntity>): RestResult<UserEntity> {
        val cacheSpName = listOf("${APPLICATION_NAME}:users:${SecurityUtil.getUsername()}", "${APPLICATION_NAME}:users:${arg.loginName}")
        val user = UserMapper.INSTANCE.convert2DO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> {
                // 加密密码
                user.password = passwordEncoder.encode(arg.password)
                if (userService.save(user)) setUserRoles(user.id, arg.roleIds?.toSet())
            }
            ModifyTypeEnum.UPDATE -> {
                if (!arg.password.isNullOrBlank()) user.password = passwordEncoder.encode(arg.password)
                if (userService.updateById(user)) setUserRoles(user.id, arg.roleIds?.toSet())
            }
            ModifyTypeEnum.REMOVE -> {
                userService.remove(Wrappers.query(user))
                user.id?.run { roleService.clearRoles(this) }
            }
        }
        // 清除新建用户及父级用户相关的所有缓存
        redisTemplate.delete(cacheSpName)
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