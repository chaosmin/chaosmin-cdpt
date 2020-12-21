package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.convert.RoleConvert
import tech.chaosmin.framework.dao.dataobject.Role
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.request.RoleReq
import tech.chaosmin.framework.domain.response.RoleResp
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
    override fun selectById(id: Long): RestResult<RoleResp?> {
        val role = roleService.getById(id)
        return if (role != null) {
            val authorities = authorityService.findAuthorities(setOf(id))
            val response = RoleConvert.INSTANCE.convert2Resp(role)
            response.authorities = authorities.mapNotNull { it.name }
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.successRestResult()
        }
    }

    override fun roleAuthorities(id: Long): RestResult<Void> {
        TODO("Not yet implemented")
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<RoleResp>> {
        val queryCondition = RequestUtil.getQueryCondition<Role>(request)
        val page = roleService.page(queryCondition.page, queryCondition.wrapper)
        return RestResultExt.successRestResult(page.convert(RoleConvert.INSTANCE::convert2Resp))
    }

    @Transactional
    override fun save(req: RoleReq): RestResult<RoleResp> {
        val role = RoleConvert.INSTANCE.convert2Entity(req)
        return if (roleService.save(role)) {
            val authorities = authorityService.updateAuthorities(role.id, req.authorityIds)
            val response = RoleConvert.INSTANCE.convert2Resp(role)
            response.authorities = authorities.mapNotNull { it.name }
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun update(id: Long, req: RoleReq): RestResult<RoleResp> {
        val role = RoleConvert.INSTANCE.convert2Entity(req).apply { this.id = id }
        return if (roleService.updateById(role)) {
            val authorities = authorityService.updateAuthorities(role.id, req.authorityIds)
            val response = RoleConvert.INSTANCE.convert2Resp(roleService.getById(role.id))
            response.authorities = authorities.mapNotNull { it.name }
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun delete(id: Long): RestResult<RoleResp> {
        return if (roleService.removeById(id)) {
            authorityService.clearAuthorities(id)
            RestResultExt.successRestResult()
        } else {
            RestResultExt.failureRestResult()
        }
    }
}