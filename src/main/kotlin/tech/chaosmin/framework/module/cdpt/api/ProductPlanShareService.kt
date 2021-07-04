package tech.chaosmin.framework.module.cdpt.api

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseShareService
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.cdpt.entity.request.ProductPlanReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductPlanResp

@Api(tags = ["产品计划操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/product-plans")
interface ProductPlanShareService : BaseShareService<ProductPlanReq, ProductPlanResp> {
    @GetMapping("/users/{id}/contract")
    fun contractPage(@PathVariable("id") id: Long): RestResult<IPage<ProductPlanResp?>>
}