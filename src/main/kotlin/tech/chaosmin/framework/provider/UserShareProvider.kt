package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.convert.UserConvert
import tech.chaosmin.framework.dao.dataobject.User
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.request.share.UserShareRequestDTO
import tech.chaosmin.framework.domain.response.share.UserShareResponseDTO
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
    override fun selectById(id: Long): RestResult<UserShareResponseDTO?> {
        val user = userService.getById(id)
        return if (user != null) {
            val roles = roleService.findRoles(id)
            val response = UserConvert.INSTANCE.convertToShareResponse(user)
            response.roles = roles.mapNotNull { it.name }
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.successRestResult()
        }
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<UserShareResponseDTO>> {
        val queryCondition = RequestUtil.getQueryCondition<User>(request)
        val result = userPageLogic.run(queryCondition)
        return RestResultExt.successRestResult(result)
    }

    @Transactional
    override fun save(requestDTO: UserShareRequestDTO): RestResult<UserShareResponseDTO> {
        val user = UserConvert.INSTANCE.convertToBaseBean(requestDTO).apply {
            this.password = passwordEncoder.encode(this.password)
        }
        return if (userService.save(user)) {
            val roles = roleService.updateRoles(user.id, requestDTO.roleIds)
            val response = UserConvert.INSTANCE.convertToShareResponse(user)
            response.roles = roles.mapNotNull { it.name }
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun update(id: Long, requestDTO: UserShareRequestDTO): RestResult<UserShareResponseDTO> {
        val user = UserConvert.INSTANCE.convertToBaseBean(requestDTO).apply {
            this.id = id
            if (!this.password.isNullOrBlank()) {
                this.password = passwordEncoder.encode(this.password)
            }
        }
        return if (userService.updateById(user)) {
            val roles = roleService.updateRoles(user.id, requestDTO.roleIds)
            val response = UserConvert.INSTANCE.convertToShareResponse(userService.getById(user.id))
            response.roles = roles.mapNotNull { it.name }
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun delete(id: Long): RestResult<UserShareResponseDTO> {
        return if (userService.removeById(id)) {
            roleService.clearRoles(id)
            RestResultExt.successRestResult()
        } else {
            RestResultExt.failureRestResult()
        }
    }
}