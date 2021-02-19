package tech.chaosmin.framework.module.mgmt.service

import org.junit.jupiter.api.Test
import org.springframework.security.core.userdetails.UserDetailsService
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.utils.JsonUtil
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/2/19 11:00
 */
class UserDetailsServiceBootTest : BaseTestMain() {
    @Resource
    lateinit var userDetailsService: UserDetailsService

    @Test
    fun loadUserByUsername() {
        val userDetails = userDetailsService.loadUserByUsername("minchao")
        println(JsonUtil.encode(userDetails, true))
    }
}