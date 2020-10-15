package tech.chaosmin.framework.service

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.dao.dataobject.User

open class UserServiceTests(private val useService: UserService) : BaseTestMain() {

    @Test
    @Rollback
    @Transactional
    open fun save() {
        val user = User()
        user.username = "test_username"
        user.loginName = "test_login_name"
        user.password = "test_password"
        Assert.assertTrue(useService.save(user))
    }

    @Test
    @Rollback
    @Transactional
    open fun removeById() {
        val user = User()
        user.username = "test_username"
        user.loginName = "test_login_name"
        user.password = "test_password"
        Assert.assertTrue(useService.save(user))
        Assert.assertTrue(useService.removeById(user.id))
    }

    @Test
    @Rollback
    @Transactional
    open fun update() {
        val user = User()
        user.username = "test_username"
        user.loginName = "test_login_name"
        user.password = "test_password"
        Assert.assertTrue(useService.saveOrUpdate(user))
        user.username = "test_username_updated"
        Assert.assertTrue(useService.saveOrUpdate(user))
    }

    @Test
    @Rollback
    @Transactional
    open fun selectById() {
        val user = User()
        user.username = "test_username"
        user.loginName = "test_login_name"
        user.password = "test_password"
        Assert.assertTrue(useService.save(user))
        Assert.assertEquals("test_username", useService.getById(user.id).username)
    }

    @Test
    fun list() {
        val list = useService.list(Wrappers.emptyWrapper())
        Assert.assertNotNull(list)
    }
}