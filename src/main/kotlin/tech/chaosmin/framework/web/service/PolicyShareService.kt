package tech.chaosmin.framework.web.service

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.response.share.PolicyShareResponseDTO
import javax.servlet.http.HttpServletRequest

@Api(tags = ["保单操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/policies")
interface PolicyShareService {
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询", response = PolicyShareResponseDTO::class)
    fun selectById(@PathVariable("id") id: Long): RestResult<PolicyShareResponseDTO?>

    @GetMapping("/page")
    @ApiOperation(value = "分页查询查询")
    fun page(request: HttpServletRequest): RestResult<IPage<PolicyShareResponseDTO>>
}