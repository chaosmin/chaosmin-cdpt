package tech.chaosmin.framework.module.mgmt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseShareService
import tech.chaosmin.framework.module.mgmt.entity.request.RoleReq
import tech.chaosmin.framework.module.mgmt.entity.response.RoleResp

@Api(tags = ["角色操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/roles")
interface RoleShareService : BaseShareService<RoleReq, RoleResp>