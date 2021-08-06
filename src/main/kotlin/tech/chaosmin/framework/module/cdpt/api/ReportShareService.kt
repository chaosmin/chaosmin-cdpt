package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.cdpt.entity.report.SltCheckReportEntity
import tech.chaosmin.framework.module.cdpt.entity.report.SltComsReportEntity
import javax.servlet.http.HttpServletResponse

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
        @PathVariable("endTime") endTime: String,
        @RequestParam(value = "timeType") timeType: String
    ): RestResult<SltComsReportEntity>

    @PostMapping("/slt-coms/{userId}/{startTime}/{endTime}")
    fun sltComsReportDownload(
        @PathVariable("userId") userId: Long,
        @PathVariable("startTime") startTime: String,
        @PathVariable("endTime") endTime: String,
        @RequestParam(value = "timeType") timeType: String,
        response: HttpServletResponse
    )

    @GetMapping("/slt-check/{startTime}/{endTime}")
    fun sltCheckReport(
        @RequestParam("userId") userId: String,
        @PathVariable("startTime") startTime: String,
        @PathVariable("endTime") endTime: String,
        @RequestParam(value = "timeType") timeType: String
    ): RestResult<SltCheckReportEntity>

    @PostMapping("/slt-check/{startTime}/{endTime}")
    fun sltCheckReportDownload(
        @RequestParam("userId") userId: String,
        @PathVariable("startTime") startTime: String,
        @PathVariable("endTime") endTime: String,
        @RequestParam(value = "timeType") timeType: String,
        response: HttpServletResponse
    )
}