package tech.chaosmin.framework.module.report.api.provider

import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.report.api.DashboardAPI
import tech.chaosmin.framework.module.report.entity.response.DashboardResp
import tech.chaosmin.framework.module.report.logic.handler.DashboardHandler
import java.util.*

/**
 * @author Romani min
 * @since 2021/7/1 22:34
 */
@RestController
open class DashboardProvider(private val dashboardHandler: DashboardHandler) : DashboardAPI {
    override fun dashboard(): RestResult<DashboardResp> {
        return dashboardHandler.operate(Date())
    }
}