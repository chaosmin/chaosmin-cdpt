package tech.chaosmin.framework.module.mgmt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.UserExt
import tech.chaosmin.framework.module.mgmt.entity.UserEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.UserMapper
import tech.chaosmin.framework.module.mgmt.service.UserService

/**
 * @author Romani min
 * @since 2020/12/17 15:47
 */
@Component
class UserQueryLogic(private val userService: UserService) : BaseQueryLogic<UserEntity, UserExt> {

    override fun get(id: Long): UserEntity? {
        val user = userService.getById(id)
        return if (user == null) null
        else UserMapper.INSTANCE.convert2Entity(user)
    }

    override fun page(cond: PageQuery<UserExt>): IPage<UserEntity> {
        val page = userService.pageExt(cond.page, cond.wrapper)
        return page.convert(UserMapper.INSTANCE::convert2Entity)
    }
}