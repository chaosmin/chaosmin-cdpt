package tech.chaosmin.framework.web.service

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.request.KnowledgeReq
import tech.chaosmin.framework.domain.response.KnowledgeResp
import javax.servlet.http.HttpServletRequest

@Api(tags = ["知识库操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/knowledge")
interface KnowledgeShareService {
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询", response = KnowledgeResp::class)
    fun selectById(@PathVariable("id") id: Long): RestResult<KnowledgeResp?>

    @GetMapping
    @ApiOperation(value = "分页查询查询")
    fun page(request: HttpServletRequest): RestResult<IPage<KnowledgeResp>>

    @PostMapping
    @ApiOperation(value = "创建/新增")
    fun save(@RequestBody req: KnowledgeReq): RestResult<KnowledgeResp>

    @PutMapping("/{id}")
    @ApiOperation(value = "修改/更新")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody req: KnowledgeReq
    ): RestResult<KnowledgeResp>

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    fun delete(@PathVariable("id") id: Long): RestResult<KnowledgeResp>
}