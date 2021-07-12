package tech.chaosmin.framework.module.mgmt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.mgmt.api.AuthorityShareService
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Authority
import tech.chaosmin.framework.module.mgmt.entity.AuthorityEntity
import tech.chaosmin.framework.module.mgmt.entity.request.AuthorityReq
import tech.chaosmin.framework.module.mgmt.entity.response.AuthorityResp
import tech.chaosmin.framework.module.mgmt.entity.response.AuthorityTreeNodeResp
import tech.chaosmin.framework.module.mgmt.handler.ModifyAuthorityHandler
import tech.chaosmin.framework.module.mgmt.handler.logic.AuthorityQueryLogic
import tech.chaosmin.framework.module.mgmt.helper.convert.AuthorityConvert
import tech.chaosmin.framework.utils.RequestUtil
import javax.servlet.http.HttpServletRequest

@RestController
open class AuthorityShareProvider(
    private val authorityQueryLogic: AuthorityQueryLogic,
    private val modifyAuthorityHandler: ModifyAuthorityHandler
) : AuthorityShareService {
    override fun selectById(id: Long): RestResult<AuthorityResp> {
        val authority = authorityQueryLogic.get(id)
        return if (authority == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(AuthorityConvert.INSTANCE.convert2Resp(authority))
    }

    override fun selectTree(): RestResult<List<AuthorityTreeNodeResp>> {
        return RestResultExt.successRestResult(authorityQueryLogic.tree())
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<AuthorityResp>> {
        val queryCondition = RequestUtil.getQueryCondition<Authority>(request)
        val page = authorityQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(AuthorityConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: AuthorityReq): RestResult<AuthorityResp> {
        val authority = AuthorityConvert.INSTANCE.convert2Entity(req).save()
        val result = modifyAuthorityHandler.operate(authority)
        return RestResultExt.mapper<AuthorityResp>(result).convert {
            AuthorityConvert.INSTANCE.convert2Resp(result.data ?: AuthorityEntity())
        }
    }

    override fun update(id: Long, req: AuthorityReq): RestResult<AuthorityResp> {
        val authority = AuthorityConvert.INSTANCE.convert2Entity(req).update(id)
        val result = modifyAuthorityHandler.operate(authority)
        return RestResultExt.mapper<AuthorityResp>(result).convert {
            AuthorityConvert.INSTANCE.convert2Resp(result.data ?: AuthorityEntity())
        }
    }

    override fun delete(id: Long): RestResult<AuthorityResp> {
        val authority = AuthorityEntity(id).remove()
        val result = modifyAuthorityHandler.operate(authority)
        return RestResultExt.mapper<AuthorityResp>(result).convert {
            AuthorityConvert.INSTANCE.convert2Resp(result.data ?: AuthorityEntity())
        }
    }
}