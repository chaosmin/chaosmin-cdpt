package tech.chaosmin.framework.module.mgmt.service.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.base.enums.YesNoEnum
import tech.chaosmin.framework.module.mgmt.domain.dao.UserDAO
import tech.chaosmin.framework.module.mgmt.domain.dataobject.User
import tech.chaosmin.framework.module.mgmt.service.UserService

@Service
open class UserServiceImpl : ServiceImpl<UserDAO, User>(), UserService {
    override fun findByLoginName(loginName: String): User? {
        val ew = Wrappers.query<User>()
            .eq("login_name", loginName)
            .eq("user.is_deleted", YesNoEnum.NO.getCode())
        val userPage = baseMapper.selectPage(Page(0, 1), ew)
        return userPage.records.firstOrNull()
    }
}