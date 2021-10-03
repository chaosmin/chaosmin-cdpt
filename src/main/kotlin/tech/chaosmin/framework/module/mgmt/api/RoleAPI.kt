package tech.chaosmin.framework.module.mgmt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseAPI
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.mgmt.entity.request.RoleReq
import tech.chaosmin.framework.module.mgmt.entity.response.RoleResp

@Api(tags = ["角色操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/roles")
interface RoleAPI : BaseAPI<RoleReq, RoleResp> {
    @GetMapping("/{id}/authorities")
    fun fetchAuthorityOfRole(@PathVariable("id") id: Long): RestResult<List<Long>>
}