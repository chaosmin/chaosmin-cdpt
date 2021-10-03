package tech.chaosmin.framework.module.cdpt.api.provider

import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.BizNoTypeEnum
import tech.chaosmin.framework.module.cdpt.api.InsureAPI
import tech.chaosmin.framework.module.cdpt.api.convert.PolicyConvert
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyResp
import tech.chaosmin.framework.module.cdpt.logic.handler.PolicyUnderwritingHandler
import tech.chaosmin.framework.utils.BizNoUtil

/**
 * @author Romani min
 * @since 2021/1/15 16:36
 */
@RestController
open class InsureShareProvider(private val policyUnderwritingHandler: PolicyUnderwritingHandler) : InsureAPI {
    override fun getBizNo(): RestResult<String> {
        val bizNo = BizNoUtil.nextBizNo(BizNoTypeEnum.DATETIME, 18)
        return RestResultExt.successRestResult(data = bizNo)
    }

    override fun insurance(req: PolicyIssueReq): RestResult<PolicyResp> {
        val result = policyUnderwritingHandler.operate(req)
        return result.convert { PolicyConvert.INSTANCE.toResp(it) }
    }
}