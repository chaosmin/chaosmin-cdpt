package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.dataobject.Authority
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.entity.AuthorityEntity
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.domain.request.AuthorityReq
import tech.chaosmin.framework.domain.response.AuthorityResp
import tech.chaosmin.framework.domain.response.AuthorityTreeNodeResp
import tech.chaosmin.framework.handler.ModifyAuthorityHandler
import tech.chaosmin.framework.handler.convert.AuthorityConvert
import tech.chaosmin.framework.handler.logic.AuthorityQueryLogic
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.AuthorityShareService
import javax.servlet.http.HttpServletRequest

@RestController
open class AuthorityShareProvider(
    private val authorityQueryLogic: AuthorityQueryLogic,
    private val modifyAuthorityHandler: ModifyAuthorityHandler
) : AuthorityShareService {
    override fun selectById(id: Long): RestResult<AuthorityResp?> {
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
        val authority = AuthorityConvert.INSTANCE.convert2Entity(req)
        authority.modifyType = ModifyTypeEnum.SAVE
        return RestResultExt.execute(modifyAuthorityHandler, authority, AuthorityConvert::class.java)
    }

    override fun update(id: Long, req: AuthorityReq): RestResult<AuthorityResp> {
        val authority = AuthorityConvert.INSTANCE.convert2Entity(req).apply { this.id = id }
        authority.modifyType = ModifyTypeEnum.UPDATE
        return RestResultExt.execute(modifyAuthorityHandler, authority, AuthorityConvert::class.java)
    }

    override fun delete(id: Long): RestResult<AuthorityResp> {
        val authority = AuthorityEntity(id)
        authority.modifyType = ModifyTypeEnum.REMOVE
        return RestResultExt.execute(modifyAuthorityHandler, authority, AuthorityConvert::class.java)
    }

}