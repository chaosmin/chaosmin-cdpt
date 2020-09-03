package tech.chaosmin.framework.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.dao.dataobject.User

interface UserService : IService<User> {
    fun findByLoginName(loginName: String): User?
}