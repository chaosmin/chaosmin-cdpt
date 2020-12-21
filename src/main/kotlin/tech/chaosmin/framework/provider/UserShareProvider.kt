package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.convert.UserConvert
import tech.chaosmin.framework.dao.dataobject.User
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.request.UserReq
import tech.chaosmin.framework.domain.response.UserResp
import tech.chaosmin.framework.handler.logic.UserPageLogic
import tech.chaosmin.framework.service.RoleService
import tech.chaosmin.framework.service.UserService
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.UserShareService
import javax.servlet.http.HttpServletRequest

@RestController
open class UserShareProvider(
    private val userService: UserService,
    private val roleService: RoleService,
    private val userPageLogic: UserPageLogic,
    private val passwordEncoder: BCryptPasswordEncoder
) : UserShareService {
    override fun selectById(id: Long): RestResult<UserResp?> {
        val user = userService.getById(id)
        return if (user != null) {
            val response = UserConvert.INSTANCE.convert2Resp(user)
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.successRestResult()
        }
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<UserResp>> {
        val queryCondition = RequestUtil.getQueryCondition<User>(request)
        val result = userPageLogic.run(queryCondition)
        return RestResultExt.successRestResult(result)
    }

    @Transactional
    override fun save(req: UserReq): RestResult<UserResp> {
        val user = UserConvert.INSTANCE.convert2Entity(req).apply {
            this.password = passwordEncoder.encode(this.password)
        }
        return if (userService.save(user)) {
            val response = UserConvert.INSTANCE.convert2Resp(user)
            if (req.roleId != null) {
                roleService.updateRoles(user.id, listOf(req.roleId!!))
            }
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun update(id: Long, req: UserReq): RestResult<UserResp> {
        val user = UserConvert.INSTANCE.convert2Entity(req).apply {
            this.id = id
            if (!this.password.isNullOrBlank()) {
                this.password = passwordEncoder.encode(this.password)
            }
        }
        return if (userService.updateById(user)) {
            val response = UserConvert.INSTANCE.convert2Resp(userService.getById(user.id))
            if (req.roleId != null) {
                roleService.updateRoles(user.id, listOf(req.roleId!!))
            }
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun delete(id: Long): RestResult<UserResp> {
        return if (userService.removeById(id)) {
            roleService.clearRoles(id)
            RestResultExt.successRestResult()
        } else {
            RestResultExt.failureRestResult()
        }
    }
}