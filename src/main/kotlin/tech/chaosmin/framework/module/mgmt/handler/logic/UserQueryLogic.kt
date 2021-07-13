package tech.chaosmin.framework.module.mgmt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.definition.SystemConst.APPLICATION_NAME
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
 * @since 2020/12/17 15:47
 */
@Component
class UserQueryLogic(private val userService: UserService) : BaseQueryLogic<UserEntity, UserExt> {
    @Resource
    lateinit var redisTemplate: RedisTemplate<String, UserEntity>

    override fun get(id: Long): UserEntity? {
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
        val cacheSpName = "$APPLICATION_NAME:users:$creator:subordinate"
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