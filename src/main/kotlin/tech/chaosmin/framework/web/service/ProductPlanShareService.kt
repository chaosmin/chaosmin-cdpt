package tech.chaosmin.framework.web.service

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.request.ProductPlanReq
import tech.chaosmin.framework.domain.response.ProductPlanResp
import javax.servlet.http.HttpServletRequest

@Api(tags = ["产品计划操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/product-plans")
interface ProductPlanShareService {
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询", response = ProductPlanResp::class)
    fun selectById(@PathVariable("id") id: Long): RestResult<ProductPlanResp?>

    @GetMapping
    @ApiOperation(value = "分页查询查询")
    fun page(request: HttpServletRequest): RestResult<IPage<ProductPlanResp>>

    @PostMapping
    @ApiOperation(value = "创建/新增")
    fun save(@RequestBody req: ProductPlanReq): RestResult<ProductPlanResp>

    @PutMapping("/{id}")
    @ApiOperation(value = "修改/更新")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody req: ProductPlanReq
    ): RestResult<ProductPlanResp>

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    fun delete(@PathVariable("id") id: Long): RestResult<ProductPlanResp>
}