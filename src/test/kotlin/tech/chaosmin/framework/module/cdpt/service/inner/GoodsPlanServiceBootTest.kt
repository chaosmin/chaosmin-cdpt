package tech.chaosmin.framework.module.cdpt.service.inner

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tech.chaosmin.framework.BaseTestMain
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/1/18 10:55
 */
class GoodsPlanServiceBootTest : BaseTestMain() {
    @Resource
    lateinit var goodsPlanService: GoodsPlanService

    @Test
    fun getProductEqUser() {
        val list = goodsPlanService.getProductIdByUser(22L)
        assertThat(list).isNotNull.isNotEmpty
    }
}