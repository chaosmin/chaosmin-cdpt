package tech.chaosmin.framework.web.service

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.request.ProductCategoryReq
import tech.chaosmin.framework.domain.response.ProductCategoryResp
import tech.chaosmin.framework.domain.response.ProductCategoryTreeNodeResp
import javax.servlet.http.HttpServletRequest

@Api(tags = ["产品大类操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/product-categories")
interface ProductCategoryShareService {
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询", response = ProductCategoryResp::class)
    fun selectById(@PathVariable("id") id: Long): RestResult<ProductCategoryResp?>

    @GetMapping("/tree")
    @ApiOperation(value = "查询权限的树状图结构")
    fun selectTree(): RestResult<List<ProductCategoryTreeNodeResp>>

    @GetMapping
    @ApiOperation(value = "分页查询查询")
    fun page(request: HttpServletRequest): RestResult<IPage<ProductCategoryResp>>

    @PostMapping
    @ApiOperation(value = "创建/新增")
    fun save(@RequestBody req: ProductCategoryReq): RestResult<ProductCategoryResp>

    @PutMapping("/{id}")
    @ApiOperation(value = "修改/更新")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody req: ProductCategoryReq
    ): RestResult<ProductCategoryResp>

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    fun delete(@PathVariable("id") id: Long): RestResult<ProductCategoryResp>
}