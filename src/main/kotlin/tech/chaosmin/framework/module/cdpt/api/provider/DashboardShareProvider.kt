package tech.chaosmin.framework.module.cdpt.api.provider

import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.api.DashboardShareService
import tech.chaosmin.framework.module.cdpt.entity.response.DashboardResp
import tech.chaosmin.framework.module.cdpt.handler.logic.PolicyQueryLogic

/**
 * @author Romani min
 * @since 2021/7/1 22:34
 */
@RestController
class DashboardShareProvider(private val policyQueryLogic: PolicyQueryLogic) : DashboardShareService {
    override fun dashboard(): RestResult<DashboardResp> {
        return RestResultExt.successRestResult(policyQueryLogic.dashboardData())
    }
}