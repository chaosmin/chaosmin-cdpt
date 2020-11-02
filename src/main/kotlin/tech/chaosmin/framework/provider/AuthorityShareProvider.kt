package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.convert.AuthorityConvert
import tech.chaosmin.framework.dao.dataobject.Authority
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.request.share.AuthorityShareRequestDTO
import tech.chaosmin.framework.domain.response.share.AuthorityShareResponseDTO
import tech.chaosmin.framework.service.AuthorityService
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.AuthorityShareService
import javax.servlet.http.HttpServletRequest

@RestController
open class AuthorityShareProvider(
    private val authorityService: AuthorityService
) : AuthorityShareService {
    override fun selectById(id: Long): RestResult<AuthorityShareResponseDTO?> {
        val authority = authorityService.getById(id)
        return if (authority != null) {
            val response = AuthorityConvert.INSTANCE.convertToShareResponse(authority)
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.successRestResult()
        }
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<AuthorityShareResponseDTO>> {
        val queryCondition = RequestUtil.getQueryCondition<Authority>(request)
        val page = authorityService.page(queryCondition.page, queryCondition.wrapper)
        return RestResultExt.successRestResult(page.convert(AuthorityConvert.INSTANCE::convertToShareResponse))
    }

    @Transactional
    override fun save(requestDTO: AuthorityShareRequestDTO): RestResult<AuthorityShareResponseDTO> {
        val authority = AuthorityConvert.INSTANCE.convertToBaseBean(requestDTO)
        return if (authorityService.save(authority)) {
            val response = AuthorityConvert.INSTANCE.convertToShareResponse(authority)
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun update(id: Long, requestDTO: AuthorityShareRequestDTO): RestResult<AuthorityShareResponseDTO> {
        val authority = AuthorityConvert.INSTANCE.convertToBaseBean(requestDTO).apply { this.id = id }
        return if (authorityService.updateById(authority)) {
            val response = AuthorityConvert.INSTANCE.convertToShareResponse(authorityService.getById(authority.id))
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun delete(id: Long): RestResult<AuthorityShareResponseDTO> {
        return if (authorityService.removeById(id)) {
            RestResultExt.successRestResult()
        } else {
            RestResultExt.failureRestResult()
        }
    }
}