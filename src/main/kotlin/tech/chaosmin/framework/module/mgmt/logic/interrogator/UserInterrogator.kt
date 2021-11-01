/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * UserInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.mgmt.logic.interrogator

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.definition.SystemConst
import tech.chaosmin.framework.module.mgmt.domain.dataobject.User
import tech.chaosmin.framework.module.mgmt.entity.LetterHeadEntity
import tech.chaosmin.framework.module.mgmt.entity.UserEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.LetterHeadMapper
import tech.chaosmin.framework.module.mgmt.helper.mapper.UserMapper
import tech.chaosmin.framework.module.mgmt.service.LetterHeadService
import tech.chaosmin.framework.module.mgmt.service.RoleService
import tech.chaosmin.framework.module.mgmt.service.UserService
import tech.chaosmin.framework.utils.SecurityUtil
import java.util.*
import java.util.concurrent.TimeUnit
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/9/4 23:48
 */
@Component
class UserInterrogator(
    private val userService: UserService,
    private val roleService: RoleService,
    private val letterHeadService: LetterHeadService
) : Interrogator<UserEntity, User> {
    @Resource
    lateinit var redisTemplate: RedisTemplate<String, UserEntity>

    override fun getOne(id: Long): UserEntity? {
        val user = userService.getById(id)
        return UserMapper.INSTANCE.convert2Entity(user)
    }

    override fun page(cond: PageQuery<User>): IPage<UserEntity> {
        var queryWrapper = cond.wrapper
        if (!SecurityUtil.getUserDetails().isAdmin) {
            // 非管理员补充过滤自身创建的账户
            queryWrapper = queryWrapper.eq("user.creator", SecurityUtil.getUsername())
        }
        val page = userService.page(cond.page, queryWrapper)
        val result: IPage<UserEntity> = page.convert(UserMapper.INSTANCE::convert2Entity)
        result.records.map { user ->
            val roles = roleService.findRoles(user.id!!)
            user.role = roles.joinToString(",") { it.name!! }
            user.roleIds = roles.map { it.id!! }
            val letters = letterHeadService.fetchByUserId(user.id!!)
            user.letterHead = letters.map { LetterHeadMapper.INSTANCE.convert2Entity(it) ?: LetterHeadEntity() }
        }
        return result
    }

    fun findSubordinate(username: String? = null): List<UserEntity> {
        val creator = username ?: SecurityUtil.getUsername()
        val cacheSpName = "${SystemConst.APPLICATION_NAME}:users:$creator:subordinate"
        return if (redisTemplate.hasKey(cacheSpName)) {
            redisTemplate.opsForSet().members(cacheSpName)?.toList() ?: emptyList()
        } else {
            val ew = Wrappers.query<User>().eq("creator", creator)
            val result = userService.list(ew).flatMap { sub ->
                val roles = roleService.findRoles(sub.id!!)
                if (roles.any { "officer" == it.code }) {
                    Collections.singleton(UserMapper.INSTANCE.convert2Entity(sub)!!)
                } else findSubordinate(sub.loginName)
            }
            result.forEach { redisTemplate.opsForSet().add(cacheSpName, it) }
            redisTemplate.expire(cacheSpName, 30, TimeUnit.MINUTES)
            result
        }
    }
}