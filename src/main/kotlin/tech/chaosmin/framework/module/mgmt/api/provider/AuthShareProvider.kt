package tech.chaosmin.framework.module.mgmt.api.provider

import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.mgmt.api.AuthShareService
import tech.chaosmin.framework.module.mgmt.entity.response.UserDetailResp
import tech.chaosmin.framework.utils.SecurityUtil

/**
 * @author Romani min
 * @since 2020/12/15 13:06
 */
@RestController
open class AuthShareProvider : AuthShareService {
    override fun getUserInfo(): RestResult<UserDetailResp> {
        val userDetails = SecurityUtil.getUserDetails()
        return RestResultExt.successRestResult(userDetails)
    }

    override fun logout(): RestResult<Void> {
        return RestResultExt.successRestResult()
    }
}