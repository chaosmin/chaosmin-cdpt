package tech.chaosmin.framework.module.mgmt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.base.BaseAPI
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.mgmt.entity.request.UserPasswordReq
import tech.chaosmin.framework.module.mgmt.entity.request.UserReq
import tech.chaosmin.framework.module.mgmt.entity.response.UserResp

@Api(tags = ["用户操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/users")
interface UserAPI : BaseAPI<UserReq, UserResp> {
    @GetMapping("/{id}/subordinate")
    fun subordinate(@PathVariable("id") id: Long): RestResult<List<UserResp?>>

    @PutMapping("/{id}/password")
    fun updatePassword(@PathVariable("id") id: Long, @RequestBody req: UserPasswordReq): RestResult<String>
}