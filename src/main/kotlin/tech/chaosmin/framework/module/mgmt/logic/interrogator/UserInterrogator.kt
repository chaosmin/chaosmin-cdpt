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
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.UserExt
import tech.chaosmin.framework.module.mgmt.entity.UserEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.UserMapper
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
class UserInterrogator(private val userService: UserService) : Interrogator<UserEntity, UserExt> {
    @Resource
    lateinit var redisTemplate: RedisTemplate<String, UserEntity>

    override fun getOne(id: Long): UserEntity? {
        val user = userService.getByIdExt(id)
        return UserMapper.INSTANCE.convert2Entity(user)
    }

    override fun page(cond: PageQuery<UserExt>): IPage<UserEntity> {
        var queryWrapper = cond.wrapper
        if (!SecurityUtil.getUserDetails().isAdmin) {
            // 非管理员补充过滤自身创建的账户
            queryWrapper = queryWrapper.eq("user.creator", SecurityUtil.getUsername())
        }
        val page = userService.pageExt(cond.page, queryWrapper)
        return page.convert(UserMapper.INSTANCE::convert2Entity)
    }

    fun findSubordinate(username: String? = null): List<UserEntity> {
        val creator = username ?: SecurityUtil.getUsername()
        val cacheSpName = "${SystemConst.APPLICATION_NAME}:users:$creator:subordinate"
        return if (redisTemplate.hasKey(cacheSpName)) {
            redisTemplate.opsForSet().members(cacheSpName)?.toList() ?: emptyList()
        } else {
            val ew = Wrappers.query<UserExt>().eq("user.creator", creator)
            val result = userService.listExt(ew).flatMap { sub ->
                if (sub.roles?.any { "officer" == it.code } == true) {
                    Collections.singleton(UserMapper.INSTANCE.convert2Entity(sub)!!)
                } else findSubordinate(sub.loginName)
            }
            result.forEach { redisTemplate.opsForSet().add(cacheSpName, it) }
            redisTemplate.expire(cacheSpName, 30, TimeUnit.MINUTES)
            result
        }
    }
}