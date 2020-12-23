package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.dataobject.Role
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.entity.RoleEntity
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.domain.request.RoleReq
import tech.chaosmin.framework.domain.response.RoleResp
import tech.chaosmin.framework.handler.ModifyRoleHandler
import tech.chaosmin.framework.handler.convert.RoleConvert
import tech.chaosmin.framework.handler.logic.RoleQueryLogic
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.RoleShareService
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
        role.modifyType = ModifyTypeEnum.SAVE
        return RestResultExt.execute(modifyRoleHandler, role, RoleConvert::class.java)
    }

    override fun update(id: Long, req: RoleReq): RestResult<RoleResp> {
        val role = RoleConvert.INSTANCE.convert2Entity(req)
        role.modifyType = ModifyTypeEnum.UPDATE
        return RestResultExt.execute(modifyRoleHandler, role, RoleConvert::class.java)
    }

    override fun delete(id: Long): RestResult<RoleResp> {
        val role = RoleEntity(id)
        role.modifyType = ModifyTypeEnum.REMOVE
        return RestResultExt.execute(modifyRoleHandler, role, RoleConvert::class.java)
    }
}