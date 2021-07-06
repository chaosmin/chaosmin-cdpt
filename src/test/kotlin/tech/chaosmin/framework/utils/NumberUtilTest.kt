package tech.chaosmin.framework.utils

import org.junit.jupiter.api.Test

/**
 * @author Romani min
 * @since 2021/7/6 17:22
 */
internal class NumberUtilTest {
    @Test
    fun test() {
        println(NumberUtil.toChinese("1234"))
        println(NumberUtil.toChinese("1,908"))
        println(NumberUtil.toChinese("-1,005"))
        println(NumberUtil.toChinese("1000.01"))
    }
}