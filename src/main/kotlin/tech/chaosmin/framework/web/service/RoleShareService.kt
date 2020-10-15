package tech.chaosmin.framework.web.service

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.request.share.RoleShareRequestDTO
import tech.chaosmin.framework.domain.response.share.RoleShareResponseDTO
import javax.servlet.http.HttpServletRequest

@Api(tags = ["角色操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/role")
interface RoleShareService {
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询", response = RoleShareResponseDTO::class)
    fun selectById(@PathVariable("id") id: Long): RestResult<RoleShareResponseDTO?>

    @GetMapping("/page")
    @ApiOperation(value = "分页查询查询")
    fun page(request: HttpServletRequest): RestResult<IPage<RoleShareResponseDTO>>

    @PostMapping
    @ApiOperation(value = "创建/新增")
    fun save(@RequestBody requestDTO: RoleShareRequestDTO): RestResult<RoleShareResponseDTO>

    @PutMapping("/{id}")
    @ApiOperation(value = "修改/更新")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody requestDTO: RoleShareRequestDTO
    ): RestResult<RoleShareResponseDTO>

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    fun delete(@PathVariable("id") id: Long): RestResult<RoleShareResponseDTO>
}