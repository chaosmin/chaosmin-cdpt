/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api.provider

import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.AbstractAPI
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.api.PolicyAPI
import tech.chaosmin.framework.module.cdpt.api.convert.PolicyConvert
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.PolicyEx
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyResp
import tech.chaosmin.framework.module.cdpt.logic.handler.PolicyModifyHandler
import tech.chaosmin.framework.module.cdpt.logic.interrogator.PolicyInterrogator
import tech.chaosmin.framework.utils.JsonUtil

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class PolicyProvider(
    private val policyInterrogator: PolicyInterrogator,
    private val policyModifyHandler: PolicyModifyHandler
) : AbstractAPI<PolicyEx, PolicyEntity, PolicyReq, PolicyResp>(
    PolicyConvert.INSTANCE, policyInterrogator, policyModifyHandler
), PolicyAPI {

    override fun policyDetail(orderNo: String): RestResult<PolicyIssueReq> {
        val paramJson = policyInterrogator.queryDetail(orderNo)
        return RestResultExt.successRestResult(JsonUtil.decode(paramJson, PolicyIssueReq::class.java)!!)
    }
}