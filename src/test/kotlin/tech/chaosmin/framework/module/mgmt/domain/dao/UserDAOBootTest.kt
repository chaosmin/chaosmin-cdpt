package tech.chaosmin.framework.module.mgmt.domain.dao

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.junit.jupiter.api.Test
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.utils.JsonUtil
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/2/19 10:12
 */
class UserDAOBootTest : BaseTestMain() {
    @Resource
    lateinit var userDAO: UserDAO

    @Test
    fun pageExt() {
        val userPage = userDAO.pageExt(Page(0, 1), Wrappers.emptyWrapper())
        println(JsonUtil.encode(userPage, true))
    }
}