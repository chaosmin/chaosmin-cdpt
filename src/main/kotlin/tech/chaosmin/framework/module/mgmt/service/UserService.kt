package tech.chaosmin.framework.module.mgmt.service

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.mgmt.domain.dataobject.User
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.UserExt

interface UserService : IService<User> {
    fun findByLoginName(loginName: String): UserExt?

    fun getByIdExt(id: Long): UserExt?

    fun listExt(queryWrapper: Wrapper<UserExt>): List<UserExt>

    fun pageExt(page: Page<UserExt>, queryWrapper: Wrapper<UserExt>): IPage<UserExt>
}