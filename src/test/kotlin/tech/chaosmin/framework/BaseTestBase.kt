package tech.chaosmin.framework

import org.junit.jupiter.api.Test

class BaseTestBase {
    @Test
    fun listMinusList() {
        val a = listOf(1, 2, 3)
        val b = listOf(2, 3, 4)
        println(a - b)
        println(b - a)
    }
}