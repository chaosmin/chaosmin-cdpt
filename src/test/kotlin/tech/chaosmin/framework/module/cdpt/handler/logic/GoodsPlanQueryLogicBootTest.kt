package tech.chaosmin.framework.module.cdpt.handler.logic

import org.junit.jupiter.api.Test
import tech.chaosmin.framework.BaseTestMain
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/1/20 15:12
 */
class GoodsPlanQueryLogicBootTest : BaseTestMain() {
    @Resource
    lateinit var goodsPlanQueryLogic: GoodsPlanQueryLogic


    @Test
    fun getGoodsCategories() {
    }
}