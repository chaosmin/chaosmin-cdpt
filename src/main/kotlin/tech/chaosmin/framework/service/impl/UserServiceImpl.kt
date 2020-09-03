package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.UserDAO
import tech.chaosmin.framework.dao.dataobject.User
import tech.chaosmin.framework.service.UserService

@Service
open class UserServiceImpl : ServiceImpl<UserDAO, User>(), UserService {
    override fun findByLoginName(loginName: String): User? {
        return baseMapper.selectOne(Wrappers.query<User>().eq("login_name", loginName))
    }
}