package tech.chaosmin.framework.web.service

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.request.AuthorityReq
import tech.chaosmin.framework.domain.response.AuthorityResp
import tech.chaosmin.framework.domain.response.AuthorityTreeNodeResp
import javax.servlet.http.HttpServletRequest

@Api(tags = ["权限操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/authorities")
interface AuthorityShareService {
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询", response = AuthorityResp::class)
    fun selectById(@PathVariable("id") id: Long): RestResult<AuthorityResp?>

    @GetMapping("/tree")
    @ApiOperation(value = "查询权限的树状图结构")
    fun selectTree(): RestResult<List<AuthorityTreeNodeResp>>

    @GetMapping
    @ApiOperation(value = "分页查询查询")
    fun page(request: HttpServletRequest): RestResult<IPage<AuthorityResp>>

    @PostMapping
    @ApiOperation(value = "创建/新增")
    fun save(@RequestBody req: AuthorityReq): RestResult<AuthorityResp>

    @PutMapping("/{id}")
    @ApiOperation(value = "修改/更新")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody req: AuthorityReq
    ): RestResult<AuthorityResp>

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    fun delete(@PathVariable("id") id: Long): RestResult<AuthorityResp>
}