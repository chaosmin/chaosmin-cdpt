package tech.chaosmin.framework.module.mgmt.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import springfox.documentation.annotations.ApiIgnore
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.mgmt.entity.response.UserDetailResp

/**
 * @author Romani min
 * @since 2020/12/15 13:00
 */
@RequestMapping("/auth")
interface AuthShareService {
    @ApiIgnore
    @GetMapping("/user/info")
    fun getUserInfo(): RestResult<UserDetailResp>

    @ApiIgnore
    @PostMapping("/user/logout")
    fun logout(): RestResult<Void>
}