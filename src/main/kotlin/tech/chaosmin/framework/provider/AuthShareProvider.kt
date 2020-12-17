package tech.chaosmin.framework.provider

import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.response.share.UserDetailShareResponseDTO
import tech.chaosmin.framework.web.service.AuthShareService

/**
 * @author Romani min
 * @since 2020/12/15 13:06
 */
@RestController
open class AuthShareProvider : AuthShareService {
    override fun getUserInfo(): RestResult<UserDetailShareResponseDTO> {
        val response = UserDetailShareResponseDTO()
        response.roles = listOf("admin")
        response.avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
        response.introduction = "I am a super administrator"
        return RestResultExt.successRestResult(response)
    }

    override fun logout(): RestResult<Void> {
        return RestResultExt.successRestResult()
    }
}