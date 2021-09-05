package tech.chaosmin.framework.module.mgmt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.UserStatusEnum
import tech.chaosmin.framework.module.mgmt.api.UserAPI
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.UserExt
import tech.chaosmin.framework.module.mgmt.entity.UserEntity
import tech.chaosmin.framework.module.mgmt.entity.request.UserPasswordReq
import tech.chaosmin.framework.module.mgmt.entity.request.UserReq
import tech.chaosmin.framework.module.mgmt.entity.response.UserResp
import tech.chaosmin.framework.module.mgmt.handler.ModifyUserHandler
import tech.chaosmin.framework.module.mgmt.helper.convert.UserConvert
import tech.chaosmin.framework.module.mgmt.logic.interrogator.UserInterrogator
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.utils.SecurityUtil
import javax.servlet.http.HttpServletRequest

@RestController
open class UserProvider(
    private val userInterrogator: UserInterrogator,
    private val modifyUserHandler: ModifyUserHandler
) : UserAPI {
    override fun subordinate(id: Long): RestResult<List<UserResp?>> {
        val loginName = SecurityUtil.getUsername()
        val subordinate = userInterrogator.findSubordinate(loginName)
        return RestResultExt.successRestResult(UserConvert.INSTANCE.convert2Resp(subordinate))
    }

    override fun updatePassword(id: Long, req: UserPasswordReq): RestResult<String> {
        val user = userInterrogator.getOne(id)
        if (user == null || user.status == UserStatusEnum.INVALID) {
            return RestResultExt.failureRestResult("指定用户不存在或已注销!")
        }
        if (modifyUserHandler.isSamePassword(user, req.password)) {
            return RestResultExt.failureRestResult("原密码输入错误, 请重新输入!")
        }
        user.password = req.newPassword
        return RestResultExt.mapper(modifyUserHandler.operate(user.update()))
    }

    override fun selectById(id: Long): RestResult<UserResp> {
        val user = userInterrogator.getOne(id)
        return if (user == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(UserConvert.INSTANCE.convert2Resp(user))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<UserResp>> {
        val queryCondition = RequestUtil.getQueryCondition<UserExt>(request)
        val page = userInterrogator.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(UserConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: UserReq): RestResult<UserResp> {
        val user = UserConvert.INSTANCE.convert2Entity(req).save()
        val result = modifyUserHandler.operate(user)
        return RestResultExt.mapper<UserResp>(result).convert {
            UserConvert.INSTANCE.convert2Resp(result.data ?: UserEntity())
        }
    }

    override fun update(id: Long, req: UserReq): RestResult<UserResp> {
        val user = UserConvert.INSTANCE.convert2Entity(req).update(id)
        val result = modifyUserHandler.operate(user)
        return RestResultExt.mapper<UserResp>(result).convert {
            UserConvert.INSTANCE.convert2Resp(result.data ?: UserEntity())
        }
    }

    override fun delete(id: Long): RestResult<UserResp> {
        val user = UserEntity(id).remove()
        val result = modifyUserHandler.operate(user)
        return RestResultExt.mapper<UserResp>(result).convert {
            UserConvert.INSTANCE.convert2Resp(result.data ?: UserEntity())
        }
    }
}