package tech.chaosmin.framework.module.cdpt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.api.PolicyShareService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyKhsResp
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyResp
import tech.chaosmin.framework.module.cdpt.handler.CancelPolicyHandler
import tech.chaosmin.framework.module.cdpt.handler.logic.PolicyQueryLogic
import tech.chaosmin.framework.module.cdpt.helper.convert.PolicyConvert
import tech.chaosmin.framework.module.cdpt.helper.convert.PolicyKhsConvert
import tech.chaosmin.framework.utils.RequestUtil
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class PolicyShareProvider(
    private val policyQueryLogic: PolicyQueryLogic,
    private val cancelPolicyHandler: CancelPolicyHandler
) : PolicyShareService {
    override fun getKhsList(id: Long): RestResult<PolicyKhsResp> {
        val policyKhsList = policyQueryLogic.queryKhs(id)
        val khsResp = PolicyKhsConvert.INSTANCE.convert2Resp(policyKhsList)
        return RestResultExt.successRestResult(khsResp ?: PolicyKhsResp())
    }

    override fun selectById(id: Long): RestResult<PolicyResp?> {
        val policy = policyQueryLogic.get(id)
        return if (policy == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(PolicyConvert.INSTANCE.convert2Resp(policy))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<PolicyResp?>> {
        val queryCondition = RequestUtil.getQueryCondition<Policy>(request)
        val page = policyQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(PolicyConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: PolicyReq): RestResult<PolicyResp> {
        throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_FUNCTION.code)
    }

    override fun update(id: Long, req: PolicyReq): RestResult<PolicyResp> {
        val restResult = cancelPolicyHandler.cancelPolicy(id)
        return RestResultExt.mapper(restResult)
    }

    override fun delete(id: Long): RestResult<PolicyResp> {
        throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_FUNCTION.code)
    }
}