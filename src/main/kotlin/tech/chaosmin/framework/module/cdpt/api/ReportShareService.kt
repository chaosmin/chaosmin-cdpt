package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.cdpt.entity.report.SltCheckReportEntity
import tech.chaosmin.framework.module.cdpt.entity.report.SltComsReportEntity

/**
 * @author Romani min
 * @since 2021/6/29 21:29
 */
@Api(tags = ["报表操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/reports")
interface ReportShareService {
    @GetMapping("/slt-coms/{userId}/{startTime}/{endTime}")
    fun sltComsReport(
        @PathVariable("userId") userId: Long,
        @PathVariable("startTime") startTime: String,
        @PathVariable("endTime") endTime: String
    ): RestResult<SltComsReportEntity>

    @GetMapping("/slt-check/{startTime}/{endTime}")
    fun sltCheckReport(
        @PathVariable("startTime") startTime: String,
        @PathVariable("endTime") endTime: String
    ): RestResult<SltCheckReportEntity>
}