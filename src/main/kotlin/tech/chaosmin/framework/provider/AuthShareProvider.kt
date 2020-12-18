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
        return RestResultExt.successRestResult(UserDetailShareResponseDTO())
    }

    override fun logout(): RestResult<Void> {
        return RestResultExt.successRestResult()
    }
}