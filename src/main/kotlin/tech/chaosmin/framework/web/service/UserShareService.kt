package tech.chaosmin.framework.web.service

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.request.share.UserShareRequestDTO
import tech.chaosmin.framework.domain.response.share.UserShareResponseDTO
import javax.servlet.http.HttpServletRequest

@Api(tags = ["用户操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/user")
interface UserShareService {
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询", response = UserShareResponseDTO::class)
    fun selectById(@PathVariable("id") id: Long): RestResult<UserShareResponseDTO?>

    @GetMapping("/page")
    @ApiOperation(value = "分页查询查询")
    fun page(request: HttpServletRequest): RestResult<IPage<UserShareResponseDTO>>

    @PostMapping
    @ApiOperation(value = "创建/新增")
    fun save(@RequestBody requestDTO: UserShareRequestDTO): RestResult<UserShareResponseDTO>

    @PutMapping("/{id}")
    @ApiOperation(value = "修改/更新")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody requestDTO: UserShareRequestDTO
    ): RestResult<UserShareResponseDTO>

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    fun delete(@PathVariable("id") id: Long): RestResult<UserShareResponseDTO>
}