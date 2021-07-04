package tech.chaosmin.framework.module.mgmt.handler.logic

import org.junit.jupiter.api.Test
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.utils.JsonUtil
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/7/1 21:16
 */
internal class UserQueryLogicBootTest : BaseTestMain() {
    @Resource
    lateinit var userQueryLogic: UserQueryLogic

    @Test
    fun get() {
        val list = userQueryLogic.findSubordinate("admin")
        println(JsonUtil.encode(list.map { it?.loginName }, true))
    }
}