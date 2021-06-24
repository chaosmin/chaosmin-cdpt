package tech.chaosmin.framework.module.mgmt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import tech.chaosmin.framework.module.mgmt.domain.dataobject.User
import tech.chaosmin.framework.module.mgmt.service.UserService

/**
 * 上下级关联处理逻辑<p>
 *
 * @author Romani min
 * @since 2021/6/24 22:30
 */
@Component
class SubordinateHandler(private val userService: UserService) {
    fun findAll(username: String): List<String> {
        val list = mutableListOf<String>()
        list.add(username)
        val ew = Wrappers.query<User>().eq("creator", username)
        val subordinate = userService.list(ew).mapNotNull { it.loginName }
        if (subordinate.isNotEmpty()) list.addAll(subordinate.flatMap { findAll(it) })
        return list
    }
}