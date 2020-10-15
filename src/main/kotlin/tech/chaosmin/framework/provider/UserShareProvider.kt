package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.convert.UserConvert
import tech.chaosmin.framework.domain.request.share.UserShareRequestDTO
import tech.chaosmin.framework.domain.response.share.UserShareResponseDTO
import tech.chaosmin.framework.service.UserService
import tech.chaosmin.framework.web.service.UserShareService
import javax.servlet.http.HttpServletRequest

@RestController
class UserShareProvider(private val userService: UserService) : UserShareService {
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
        TODO("Not yet implemented")
    }

    override fun save(requestDTO: UserShareRequestDTO): RestResult<UserShareResponseDTO> {
        TODO("Not yet implemented")
    }

    override fun update(id: Long, requestDTO: UserShareRequestDTO): RestResult<UserShareResponseDTO> {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long): RestResult<UserShareResponseDTO> {
        TODO("Not yet implemented")
    }
}