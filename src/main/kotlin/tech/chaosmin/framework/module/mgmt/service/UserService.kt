package tech.chaosmin.framework.module.mgmt.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.mgmt.domain.dataobject.User

interface UserService : IService<User> {
    fun findByLoginName(loginName: String): User?
}