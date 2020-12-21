package tech.chaosmin.framework.web.service

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.request.UserReq
import tech.chaosmin.framework.domain.response.UserResp
import javax.servlet.http.HttpServletRequest

@Api(tags = ["用户操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/users")
interface UserShareService {
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询", response = UserResp::class)
    fun selectById(@PathVariable("id") id: Long): RestResult<UserResp?>

    @GetMapping
    @ApiOperation(value = "分页查询查询")
    fun page(request: HttpServletRequest): RestResult<IPage<UserResp>>

    @PostMapping
    @ApiOperation(value = "创建/新增")
    fun save(@RequestBody req: UserReq): RestResult<UserResp>

    @PutMapping("/{id}")
    @ApiOperation(value = "修改/更新")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody req: UserReq
    ): RestResult<UserResp>

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    fun delete(@PathVariable("id") id: Long): RestResult<UserResp>
}