package tech.chaosmin.framework.utils

import org.junit.jupiter.api.Test
import tech.chaosmin.framework.utils.StringUtil.getCodeFromZh

/**
 * @author Romani min
 * @since 2020/12/26 18:02
 */
internal class StringUtilTest {
    @Test
    fun getCodeFromZh() {
        println("闵超".getCodeFromZh())
        println("闵超".getCodeFromZh(6))
        println("闵超".getCodeFromZh(8))
        println("安游保".getCodeFromZh())
        println("安游保".getCodeFromZh(6))
        println("安游保".getCodeFromZh(8))
        println("蚂蚁花呗".getCodeFromZh())
        println("蚂蚁花呗".getCodeFromZh(6))
        println("蚂蚁花呗".getCodeFromZh(8))
        println("平安保险".getCodeFromZh())
        println("平安保险".getCodeFromZh(6))
        println("平安保险".getCodeFromZh(8))
        println("平安好车主APP".getCodeFromZh())
        println("平安好车主APP".getCodeFromZh(6))
        println("平安好车主APP".getCodeFromZh(8))
        println("达尔文重疾险".getCodeFromZh())
        println("达尔文重疾险".getCodeFromZh(6))
        println("达尔文重疾险达尔文重疾险".getCodeFromZh(8))
    }
}