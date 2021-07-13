package tech.chaosmin.framework.module.cdpt.handler

import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.PolicyProcessEnum
import tech.chaosmin.framework.base.enums.PolicyStatusEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.DDResp
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj.DDCPResp
import tech.chaosmin.framework.module.cdpt.handler.logic.PolicyQueryLogic
import tech.chaosmin.framework.module.cdpt.helper.convert.ChannelRequestConvert
import tech.chaosmin.framework.module.cdpt.service.external.impl.DadiChannelRequestService
import tech.chaosmin.framework.utils.JsonUtil
import java.util.*

/**
 * @author Romani min
 * @since 2021/6/24 21:46
 */
@Component
open class CancelPolicyHandler(
    private val policyQueryLogic: PolicyQueryLogic,
    private val modifyPolicyHandler: ModifyPolicyHandler,
    private val dadiChannelRequestService: DadiChannelRequestService
) {
    fun cancelPolicy(id: Long): RestResult<PolicyEntity> {
        val policy = policyQueryLogic.get(id) ?: throw FrameworkException(ErrorCodeEnum.RESOURCE_NOT_EXIST.code, "保单")
        if (policy.status == PolicyStatusEnum.REFUND) {
            throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_PARAM_TYPE.code, "保单已退保")
        }
        if (policy.effectiveTime!! < Date()) {
            throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_PARAM_TYPE.code, "保单已生效")
        }
        var result = RestResultExt.successRestResult(policy)
        val ddReq = ChannelRequestConvert.convert2DDCPReq(policy)
        dadiChannelRequestService.request(PolicyProcessEnum.POLICY_CANCEL, ddReq) {
            val ddcpRespEntity = JsonUtil.decode(it, DDResp::class.java, DDCPResp::class.java)
            if (ddcpRespEntity?.responseBody is DDCPResp && "0" == ddcpRespEntity.responseHead?.resultCode) {
                policy.status = PolicyStatusEnum.REFUND
                modifyPolicyHandler.operate(policy.update())
            } else result = RestResultExt.failureRestResult(msg = ddcpRespEntity?.responseHead?.appMessage ?: "", data = policy)
        }
        return result
    }
}