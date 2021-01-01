package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseShareService
import tech.chaosmin.framework.module.cdpt.entity.request.PartnerReq
import tech.chaosmin.framework.module.cdpt.entity.response.PartnerResp

@Api(tags = ["保险公司操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/partners")
interface PartnerShareService : BaseShareService<PartnerReq, PartnerResp>