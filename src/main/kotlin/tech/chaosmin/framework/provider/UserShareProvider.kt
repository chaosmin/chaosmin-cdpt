package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.dataobject.User
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.convert.UserConvert
import tech.chaosmin.framework.domain.request.share.UserShareRequestDTO
import tech.chaosmin.framework.domain.response.share.UserShareResponseDTO
import tech.chaosmin.framework.service.UserService
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.UserShareService
import javax.servlet.http.HttpServletRequest

@RestController
open class UserShareProvider(
    private val userService: UserService,
    private val passwordEncoder: BCryptPasswordEncoder
) : UserShareService {
    override fun selectById(id: Long): RestResult<UserShareResponseDTO?> {
        val user = userService.getById(id)
        return if (user != null) {
            val response = UserConvert.INSTANCE.convertToShareResponse(user)
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.successRestResult()
        }
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<UserShareResponseDTO>> {
        val queryCondition = RequestUtil.getQueryCondition<User>(request)
        val page = userService.page(queryCondition.page, queryCondition.wrapper)
        return RestResultExt.successRestResult(page.convert(UserConvert.INSTANCE::convertToShareResponse))
    }

    override fun save(requestDTO: UserShareRequestDTO): RestResult<UserShareResponseDTO> {
        val user = UserConvert.INSTANCE.convertToBaseBean(requestDTO).apply {
            this.password = passwordEncoder.encode(this.password)
        }
        return if (userService.save(user)) {
            val response = UserConvert.INSTANCE.convertToShareResponse(user)
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    override fun update(id: Long, requestDTO: UserShareRequestDTO): RestResult<UserShareResponseDTO> {
        val user = UserConvert.INSTANCE.convertToBaseBean(requestDTO).apply {
            this.password = passwordEncoder.encode(this.password)
            this.id = id
        }
        return if (userService.updateById(user)) {
            val response = UserConvert.INSTANCE.convertToShareResponse(user)
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    override fun delete(id: Long): RestResult<UserShareResponseDTO> {
        return if (userService.removeById(id)) {
            RestResultExt.successRestResult()
        } else {
            RestResultExt.failureRestResult()
        }
    }
}