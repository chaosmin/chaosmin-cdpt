package tech.chaosmin.framework.module.mgmt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.UserExt
import tech.chaosmin.framework.module.mgmt.entity.UserEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.UserMapper
import tech.chaosmin.framework.module.mgmt.service.UserService
import tech.chaosmin.framework.utils.SecurityUtil
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/17 15:47
 */
@Component
class UserQueryLogic(private val userService: UserService) : BaseQueryLogic<UserEntity, UserExt> {

    override fun get(id: Long): UserEntity? {
        val user = userService.getByIdExt(id)
        return UserMapper.INSTANCE.convertEx2Entity(user)
    }

    override fun page(cond: PageQuery<UserExt>): IPage<UserEntity?> {
        var queryWrapper = cond.wrapper
        if (!SecurityUtil.getUserDetails().isAdmin) {
            // 非管理员补充过滤自身创建的账户
            queryWrapper = queryWrapper.eq("user.creator", SecurityUtil.getUsername())
        }
        val page = userService.pageExt(cond.page, queryWrapper)
        return page.convert(UserMapper.INSTANCE::convertEx2Entity)
    }

    fun findSubordinate(username: String? = null): List<UserEntity?> {
        val creator = username ?: SecurityUtil.getUsername()
        val ew = Wrappers.query<UserExt>().eq("user.creator", creator)
        return userService.listExt(ew).flatMap { sub ->
            if (sub.roles?.any { "officer" == it.code } == true) {
                Collections.singleton(UserMapper.INSTANCE.convertEx2Entity(sub))
            } else findSubordinate(sub.loginName)
        }
    }
}