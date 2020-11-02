package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.convert.RoleConvert
import tech.chaosmin.framework.dao.dataobject.Role
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.request.share.RoleShareRequestDTO
import tech.chaosmin.framework.domain.response.share.RoleShareResponseDTO
import tech.chaosmin.framework.service.AuthorityService
import tech.chaosmin.framework.service.RoleService
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.RoleShareService
import javax.servlet.http.HttpServletRequest

@RestController
open class RoleShareProvider(
    private val roleService: RoleService,
    private val authorityService: AuthorityService
) : RoleShareService {
    override fun selectById(id: Long): RestResult<RoleShareResponseDTO?> {
        val role = roleService.getById(id)
        return if (role != null) {
            val authorities = authorityService.findAuthorities(setOf(id))
            val response = RoleConvert.INSTANCE.convertToShareResponse(role)
            response.authorities = authorities.mapNotNull { it.name }
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.successRestResult()
        }
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<RoleShareResponseDTO>> {
        val queryCondition = RequestUtil.getQueryCondition<Role>(request)
        val page = roleService.page(queryCondition.page, queryCondition.wrapper)
        return RestResultExt.successRestResult(page.convert(RoleConvert.INSTANCE::convertToShareResponse))
    }

    @Transactional
    override fun save(requestDTO: RoleShareRequestDTO): RestResult<RoleShareResponseDTO> {
        val role = RoleConvert.INSTANCE.convertToBaseBean(requestDTO)
        return if (roleService.save(role)) {
            val authorities = authorityService.updateAuthorities(role.id, requestDTO.authorityIds)
            val response = RoleConvert.INSTANCE.convertToShareResponse(role)
            response.authorities = authorities.mapNotNull { it.name }
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun update(id: Long, requestDTO: RoleShareRequestDTO): RestResult<RoleShareResponseDTO> {
        val role = RoleConvert.INSTANCE.convertToBaseBean(requestDTO).apply { this.id = id }
        return if (roleService.updateById(role)) {
            val authorities = authorityService.updateAuthorities(role.id, requestDTO.authorityIds)
            val response = RoleConvert.INSTANCE.convertToShareResponse(roleService.getById(role.id))
            response.authorities = authorities.mapNotNull { it.name }
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun delete(id: Long): RestResult<RoleShareResponseDTO> {
        return if (roleService.removeById(id)) {
            authorityService.clearAuthorities(id)
            RestResultExt.successRestResult()
        } else {
            RestResultExt.failureRestResult()
        }
    }
}