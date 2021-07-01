package tech.chaosmin.framework.module.mgmt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.mgmt.api.UserShareService
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.UserExt
import tech.chaosmin.framework.module.mgmt.entity.UserEntity
import tech.chaosmin.framework.module.mgmt.entity.request.UserReq
import tech.chaosmin.framework.module.mgmt.entity.response.UserResp
import tech.chaosmin.framework.module.mgmt.handler.ModifyUserHandler
import tech.chaosmin.framework.module.mgmt.handler.logic.UserQueryLogic
import tech.chaosmin.framework.module.mgmt.helper.convert.UserConvert
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.utils.SecurityUtil
import javax.servlet.http.HttpServletRequest

@RestController
open class UserShareProvider(
    private val userQueryLogic: UserQueryLogic,
    private val modifyUserHandler: ModifyUserHandler
) : UserShareService {
    override fun subordinate(id: Long): RestResult<List<UserResp?>> {
        val loginName = SecurityUtil.getUsername()
        val subordinate = userQueryLogic.findSubordinate(loginName)
        return RestResultExt.successRestResult(UserConvert.INSTANCE.convert2Resp(subordinate))
    }

    override fun selectById(id: Long): RestResult<UserResp?> {
        val user = userQueryLogic.get(id)
        return if (user == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(UserConvert.INSTANCE.convert2Resp(user))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<UserResp?>> {
        val queryCondition = RequestUtil.getQueryCondition<UserExt>(request)
        val page = userQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(UserConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: UserReq): RestResult<UserResp> {
        val user = UserConvert.INSTANCE.convert2Entity(req)
        user.save()
        return RestResultExt.execute(modifyUserHandler, user, UserConvert::class.java)
    }

    override fun update(id: Long, req: UserReq): RestResult<UserResp> {
        val user = UserConvert.INSTANCE.convert2Entity(req)
        user.update(id)
        return RestResultExt.execute(modifyUserHandler, user, UserConvert::class.java)
    }

    override fun delete(id: Long): RestResult<UserResp> {
        val user = UserEntity(id)
        user.remove()
        return RestResultExt.execute(modifyUserHandler, user, UserConvert::class.java)
    }
}