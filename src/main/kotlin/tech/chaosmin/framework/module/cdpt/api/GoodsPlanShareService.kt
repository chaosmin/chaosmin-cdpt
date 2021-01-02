package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseShareService
import tech.chaosmin.framework.module.cdpt.entity.request.GoodsPlanReq
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsPlanResp

@Api(tags = ["个人产品计划操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/goods-plans")
interface GoodsPlanShareService : BaseShareService<GoodsPlanReq, GoodsPlanResp>