package tech.chaosmin.framework.module.cdpt.api.provider

import cn.hutool.core.date.DateUtil
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.api.ReportShareService
import tech.chaosmin.framework.module.cdpt.entity.report.SltCheckReportEntity
import tech.chaosmin.framework.module.cdpt.entity.report.SltComsReportEntity
import tech.chaosmin.framework.module.cdpt.handler.ReportSltCheckHandler
import tech.chaosmin.framework.module.cdpt.handler.ReportSltComsHandler

/**
 * @author Romani min
 * @since 2021/6/29 21:32
 */
@RestController
class ReportShareProvider(
    private val reportSltComsHandler: ReportSltComsHandler,
    private val reportSltCheckHandler: ReportSltCheckHandler
) : ReportShareService {
    override fun sltComsReport(userId: Long, startTime: String, endTime: String): RestResult<SltComsReportEntity> {
        val req = SltComsReportEntity().apply {
            this.userId = userId
            this.statisticsStartTime = DateUtil.beginOfDay(DateUtil.parse(startTime, "yyyy-MM-dd"))
            this.statisticsEndTime = DateUtil.endOfDay((DateUtil.parse(endTime, "yyyy-MM-dd")))
        }
        val result = reportSltComsHandler.operate(req)
        return RestResultExt.mapper<SltComsReportEntity>(result).apply { this.data = result.data }
    }

    override fun sltCheckReport(startTime: String, endTime: String): RestResult<SltCheckReportEntity> {
        val req = SltCheckReportEntity().apply {
            this.statisticsStartTime = DateUtil.beginOfDay(DateUtil.parse(startTime, "yyyy-MM-dd"))
            this.statisticsEndTime = DateUtil.endOfDay((DateUtil.parse(endTime, "yyyy-MM-dd")))
        }
        val result = reportSltCheckHandler.operate(req)
        return RestResultExt.mapper<SltCheckReportEntity>(result).apply { this.data = result.data }
    }
}