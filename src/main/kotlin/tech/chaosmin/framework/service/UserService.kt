package tech.chaosmin.framework.service

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.dao.dataobject.User
import tech.chaosmin.framework.dao.dataobject.ext.UserExt

interface UserService : IService<User> {
    fun findByLoginName(loginName: String): User?

    fun pageExt(page: Page<UserExt>, queryWrapper: Wrapper<UserExt>): IPage<UserExt>
}