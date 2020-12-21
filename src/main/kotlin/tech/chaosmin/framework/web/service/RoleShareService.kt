package tech.chaosmin.framework.web.service

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.request.RoleReq
import tech.chaosmin.framework.domain.response.RoleAuthorityResp
import tech.chaosmin.framework.domain.response.RoleResp
import javax.servlet.http.HttpServletRequest

@Api(tags = ["角色操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/roles")
interface RoleShareService {
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询", response = RoleResp::class)
    fun selectById(@PathVariable("id") id: Long): RestResult<RoleResp?>

    @GetMapping("/{id}/authorities")
    @ApiOperation(value = "查询指定id的角色权限", response = RoleResp::class)
    fun roleAuthorities(@PathVariable("id") id: Long): RestResult<List<RoleAuthorityResp>>

    @GetMapping
    @ApiOperation(value = "分页查询查询")
    fun page(request: HttpServletRequest): RestResult<IPage<RoleResp>>

    @PostMapping
    @ApiOperation(value = "创建/新增")
    fun save(@RequestBody req: RoleReq): RestResult<RoleResp>

    @PutMapping("/{id}")
    @ApiOperation(value = "修改/更新")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody req: RoleReq
    ): RestResult<RoleResp>

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    fun delete(@PathVariable("id") id: Long): RestResult<RoleResp>
}