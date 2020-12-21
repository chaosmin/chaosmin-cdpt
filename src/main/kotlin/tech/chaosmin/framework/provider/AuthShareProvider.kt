package tech.chaosmin.framework.provider

import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.response.UserDetailResp
import tech.chaosmin.framework.utils.SecurityUtil
import tech.chaosmin.framework.web.service.AuthShareService

/**
 * @author Romani min
 * @since 2020/12/15 13:06
 */
@RestController
open class AuthShareProvider : AuthShareService {
    override fun getUserInfo(): RestResult<UserDetailResp> {
        val userDetails = SecurityUtil.getUserDetails()
        return if (userDetails != null) {
            RestResultExt.successRestResult(userDetails)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    override fun logout(): RestResult<Void> {
        return RestResultExt.successRestResult()
    }
}