package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseShareService
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyResp

@Api(tags = ["保单操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/policies")
interface PolicyShareService : BaseShareService<PolicyReq, PolicyResp>