package tech.chaosmin.framework.utils

import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * @author Romani min
 * @since 2020/12/26 18:02
 */
internal class StringUtilTest {
    @Test
    fun format() {
        println(DecimalFormat(",###,##0").format(BigDecimal("1000000")))
        println(DecimalFormat(",###,##0").format(BigDecimal("100000")))
        println(DecimalFormat(",###,##0").format(BigDecimal("10000")))
        println(DecimalFormat(",###,##0").format(BigDecimal("1000")))
        println(DecimalFormat(",###,##0").format(BigDecimal("100")))
    }
}