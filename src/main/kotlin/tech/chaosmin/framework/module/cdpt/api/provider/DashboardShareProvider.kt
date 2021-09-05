package tech.chaosmin.framework.module.cdpt.api.provider

import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.api.DashboardAPI
import tech.chaosmin.framework.module.cdpt.entity.response.DashboardResp
import tech.chaosmin.framework.module.cdpt.logic.interrogator.PolicyInterrogator

/**
 * @author Romani min
 * @since 2021/7/1 22:34
 */
@RestController
open class DashboardShareProvider(private val policyInterrogator: PolicyInterrogator) : DashboardAPI {
    override fun dashboard(): RestResult<DashboardResp> {
        return RestResultExt.successRestResult(policyInterrogator.dashboardData())
    }
}