package tech.chaosmin.framework.web.service

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.request.ProductPlanRateTableReq
import tech.chaosmin.framework.domain.response.ProductPlanRateTableResp
import javax.servlet.http.HttpServletRequest

@Api(tags = ["知识库操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/product-plan-rate-table")
interface ProductPlanRateTableShareService {
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询", response = ProductPlanRateTableResp::class)
    fun selectById(@PathVariable("id") id: Long): RestResult<ProductPlanRateTableResp?>

    @GetMapping
    @ApiOperation(value = "分页查询查询")
    fun page(request: HttpServletRequest): RestResult<IPage<ProductPlanRateTableResp>>

    @PostMapping
    @ApiOperation(value = "创建/新增")
    fun save(@RequestBody req: ProductPlanRateTableReq): RestResult<ProductPlanRateTableResp>

    @PutMapping("/{id}")
    @ApiOperation(value = "修改/更新")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody req: ProductPlanRateTableReq
    ): RestResult<ProductPlanRateTableResp>

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    fun delete(@PathVariable("id") id: Long): RestResult<ProductPlanRateTableResp>
}