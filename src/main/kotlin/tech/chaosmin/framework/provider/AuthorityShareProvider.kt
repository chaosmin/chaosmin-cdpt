package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.convert.AuthorityConvert
import tech.chaosmin.framework.dao.dataobject.Authority
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.request.AuthorityReq
import tech.chaosmin.framework.domain.response.AuthorityResp
import tech.chaosmin.framework.service.AuthorityService
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.AuthorityShareService
import javax.servlet.http.HttpServletRequest

@RestController
open class AuthorityShareProvider(private val authorityService: AuthorityService) : AuthorityShareService {
    override fun selectById(id: Long): RestResult<AuthorityResp?> {
        val authority = authorityService.getById(id)
        return if (authority != null) {
            val response = AuthorityConvert.INSTANCE.convert2Resp(authority)
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.successRestResult()
        }
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<AuthorityResp>> {
        val queryCondition = RequestUtil.getQueryCondition<Authority>(request)
        val page = authorityService.page(queryCondition.page, queryCondition.wrapper)
        return RestResultExt.successRestResult(page.convert(AuthorityConvert.INSTANCE::convert2Resp))
    }

    @Transactional
    override fun save(req: AuthorityReq): RestResult<AuthorityResp> {
        val authority = AuthorityConvert.INSTANCE.convert2Entity(req)
        return if (authorityService.save(authority)) {
            val response = AuthorityConvert.INSTANCE.convert2Resp(authority)
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun update(id: Long, req: AuthorityReq): RestResult<AuthorityResp> {
        val authority = AuthorityConvert.INSTANCE.convert2Entity(req).apply { this.id = id }
        return if (authorityService.updateById(authority)) {
            val response = AuthorityConvert.INSTANCE.convert2Resp(authorityService.getById(authority.id))
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun delete(id: Long): RestResult<AuthorityResp> {
        return if (authorityService.removeById(id)) {
            RestResultExt.successRestResult()
        } else {
            RestResultExt.failureRestResult()
        }
    }
}