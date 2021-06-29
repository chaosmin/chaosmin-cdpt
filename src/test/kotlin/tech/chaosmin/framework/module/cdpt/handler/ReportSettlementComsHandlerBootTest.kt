package tech.chaosmin.framework.module.cdpt.handler

import cn.hutool.core.date.DateUtil
import org.junit.jupiter.api.Test
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.module.cdpt.entity.SettlementComsReportEntity
import tech.chaosmin.framework.utils.JsonUtil
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/6/29 16:34
 */
internal class ReportSettlementComsHandlerBootTest : BaseTestMain() {
    @Resource
    lateinit var reportSettlementComsHandler: ReportSettlementComsHandler

    @Test
    fun operate() {
        val req = SettlementComsReportEntity().apply {
            this.userId = 47L
            this.statisticsStartTime = DateUtil.parse("2021-06-01 00:00:00", "yyyy-MM-dd HH:mm:ss")
            this.statisticsEndTime = DateUtil.parse("2021-06-30 23:59:59", "yyyy-MM-dd HH:mm:ss")
        }
        val result = reportSettlementComsHandler.operate(req)
        println(JsonUtil.encode(result, true))
    }
}