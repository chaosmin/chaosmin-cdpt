package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseShareService
import tech.chaosmin.framework.module.cdpt.entity.request.ProductPlanReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductPlanResp

@Api(tags = ["产品计划操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/product-plans")
interface ProductPlanShareService : BaseShareService<ProductPlanReq, ProductPlanResp>