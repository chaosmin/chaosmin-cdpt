package tech.chaosmin.framework.module.mgmt.service

import org.junit.jupiter.api.Test
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.utils.JsonUtil
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/2/19 11:16
 */
class UserServiceBootTest : BaseTestMain() {
    @Resource
    lateinit var userService: UserService

    @Test
    fun findByLoginName() {
        val user = userService.findByLoginName("minchao")
        println(JsonUtil.encode(user, true))
    }
}