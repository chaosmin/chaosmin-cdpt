package tech.chaosmin.framework.web.service

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import springfox.documentation.annotations.ApiIgnore
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.response.share.UserDetailShareResponseDTO

/**
 * @author Romani min
 * @since 2020/12/15 13:00
 */
@ApiIgnore
@RequestMapping("/auth")
interface AuthShareService {
    @GetMapping("/user/info")
    fun getUserInfo(): RestResult<UserDetailShareResponseDTO>

    @PostMapping("/user/logout")
    fun logout(): RestResult<Void>
}