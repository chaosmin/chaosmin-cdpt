package tech.chaosmin.framework.module.mgmt.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseAPI
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.mgmt.entity.request.AuthorityReq
import tech.chaosmin.framework.module.mgmt.entity.response.AuthorityResp
import tech.chaosmin.framework.module.mgmt.entity.response.AuthorityTreeNodeResp

@Api(tags = ["权限操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/authorities")
interface AuthorityAPI : BaseAPI<AuthorityReq, AuthorityResp> {

    @GetMapping("/tree")
    @ApiOperation(value = "查询权限的树状图结构")
    fun selectTree(): RestResult<List<AuthorityTreeNodeResp>>
}