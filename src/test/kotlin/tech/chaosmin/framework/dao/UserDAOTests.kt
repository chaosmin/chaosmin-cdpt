package tech.chaosmin.framework.dao

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.dao.dataobject.User
import javax.annotation.Resource

open class UserDAOTests : BaseTestMain() {
    @Resource
    private lateinit var userDAO: UserDAO

    @Test
    @Rollback
    @Transactional
    open fun insert() {
        val user = User()
        user.username = "test_username"
        user.loginName = "test_login_name"
        user.password = "test_password"
        Assert.assertEquals(1, userDAO.insert(user))
    }

    @Test
    @Rollback
    @Transactional
    open fun delete() {
        val user = User()
        user.username = "test_username"
        user.loginName = "test_login_name"
        user.password = "test_password"
        Assert.assertEquals(1, userDAO.insert(user))
        Assert.assertEquals(1, userDAO.deleteById(user.id))
    }

    @Test
    @Rollback
    @Transactional
    open fun update() {
        val user = User()
        user.username = "test_username"
        user.loginName = "test_login_name"
        user.password = "test_password"
        Assert.assertEquals(1, userDAO.insert(user))
        user.username = "test_username_updated"
        Assert.assertEquals(1, userDAO.updateById(user))
    }

    @Test
    @Rollback
    @Transactional
    open fun selectById() {
        val user = User()
        user.username = "test_username"
        user.loginName = "test_login_name"
        user.password = "test_password"
        Assert.assertEquals(1, userDAO.insert(user))
        Assert.assertEquals("test_username", userDAO.selectById(user.id).username)
    }

    @Test
    fun list() {
        val list = userDAO.selectList(Wrappers.emptyWrapper())
        Assert.assertNotNull(list)
    }
}