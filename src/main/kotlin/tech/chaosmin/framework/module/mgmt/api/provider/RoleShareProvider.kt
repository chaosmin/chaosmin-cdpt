package tech.chaosmin.framework.module.mgmt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.mgmt.api.RoleShareService
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Role
import tech.chaosmin.framework.module.mgmt.entity.RoleEntity
import tech.chaosmin.framework.module.mgmt.entity.request.RoleReq
import tech.chaosmin.framework.module.mgmt.entity.response.RoleResp
import tech.chaosmin.framework.module.mgmt.handler.ModifyRoleHandler
import tech.chaosmin.framework.module.mgmt.handler.logic.RoleQueryLogic
import tech.chaosmin.framework.module.mgmt.helper.convert.RoleConvert
import tech.chaosmin.framework.utils.RequestUtil
import javax.servlet.http.HttpServletRequest

@RestController
open class RoleShareProvider(
    private val roleQueryLogic: RoleQueryLogic,
    private val modifyRoleHandler: ModifyRoleHandler
) : RoleShareService {
    override fun selectById(id: Long): RestResult<RoleResp?> {
        val role = roleQueryLogic.get(id)
        return if (role == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(RoleConvert.INSTANCE.convert2Resp(role))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<RoleResp>> {
        val queryCondition = RequestUtil.getQueryCondition<Role>(request)
        val page = roleQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(RoleConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: RoleReq): RestResult<RoleResp> {
        val role = RoleConvert.INSTANCE.convert2Entity(req)
        role.save()
        return RestResultExt.execute(modifyRoleHandler, role, RoleConvert::class.java)
    }

    override fun update(id: Long, req: RoleReq): RestResult<RoleResp> {
        val role = RoleConvert.INSTANCE.convert2Entity(req)
        role.update(id)
        return RestResultExt.execute(modifyRoleHandler, role, RoleConvert::class.java)
    }

    override fun delete(id: Long): RestResult<RoleResp> {
        val role = RoleEntity(id)
        role.remove()
        return RestResultExt.execute(modifyRoleHandler, role, RoleConvert::class.java)
    }
}