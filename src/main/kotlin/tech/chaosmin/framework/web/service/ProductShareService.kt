package tech.chaosmin.framework.web.service

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.request.ProductReq
import tech.chaosmin.framework.domain.request.UploadFileReq
import tech.chaosmin.framework.domain.response.ProductResp
import javax.servlet.http.HttpServletRequest

@Api(tags = ["产品操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/products")
interface ProductShareService {
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询", response = ProductResp::class)
    fun selectById(@PathVariable("id") id: Long): RestResult<ProductResp?>

    @GetMapping
    @ApiOperation(value = "分页查询查询")
    fun page(request: HttpServletRequest): RestResult<IPage<ProductResp>>

    @PostMapping
    @ApiOperation(value = "创建/新增")
    fun save(@RequestBody req: ProductReq): RestResult<ProductResp>

    @PostMapping("/file")
    @ApiOperation("上传添加")
    fun upload(req: UploadFileReq): RestResult<ProductResp>

    @PutMapping("/{id}")
    @ApiOperation(value = "修改/更新")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody req: ProductReq
    ): RestResult<ProductResp>

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    fun delete(@PathVariable("id") id: Long): RestResult<ProductResp>
}