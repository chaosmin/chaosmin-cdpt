package tech.chaosmin.framework.handler.base

import cn.hutool.core.lang.Assert
import org.junit.jupiter.api.Test
import tech.chaosmin.framework.domain.RestResult

internal class RetryTemplateOperateTest {
    class TestRetryTemplateOperate : RetryTemplateOperate<String, String>("testRetry") {
        @Suppress("DIVISION_BY_ZERO")
        override fun processor(arg: String, result: RestResult<String>): RestResult<String> {
            val number = 1 / arg.toInt()
            return result.success(number.toString())
        }
    }

    @Test
    fun operate() {
        val operate = TestRetryTemplateOperate().setRetryTimes(5).setSleepTime(5000)
        Assert.notNull(operate.operate("0"))
        Assert.notNull(operate.operate("1"))
        Assert.notNull(operate.progressiveWait(true).printWarnings(true).operate("A"))
    }
}