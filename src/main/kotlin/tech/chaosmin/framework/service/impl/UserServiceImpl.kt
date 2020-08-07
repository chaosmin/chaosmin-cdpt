package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.UserDAO
import tech.chaosmin.framework.dao.dataobject.User
import tech.chaosmin.framework.service.UserService

@Service
open class UserServiceImpl : ServiceImpl<UserDAO, User>(), UserService