package tech.chaosmin.framework.web.service

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.request.share.GoodsShareRequestDTO
import tech.chaosmin.framework.domain.response.share.GoodsShareResponseDTO
import javax.servlet.http.HttpServletRequest

@Api(tags = ["产品操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/policies")
interface GoodsShareService {
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询", response = GoodsShareResponseDTO::class)
    fun selectById(@PathVariable("id") id: Long): RestResult<GoodsShareResponseDTO?>

    @GetMapping("/page")
    @ApiOperation(value = "分页查询查询")
    fun page(request: HttpServletRequest): RestResult<IPage<GoodsShareResponseDTO>>

    @PostMapping
    @ApiOperation(value = "创建/新增")
    fun save(@RequestBody requestDTO: GoodsShareRequestDTO): RestResult<GoodsShareResponseDTO>

    @PutMapping("/{id}")
    @ApiOperation(value = "修改/更新")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody requestDTO: GoodsShareRequestDTO
    ): RestResult<GoodsShareResponseDTO>

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    fun delete(@PathVariable("id") id: Long): RestResult<GoodsShareResponseDTO>
}