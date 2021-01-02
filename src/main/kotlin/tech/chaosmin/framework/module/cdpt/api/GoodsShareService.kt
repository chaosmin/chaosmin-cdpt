package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseShareService
import tech.chaosmin.framework.module.cdpt.entity.request.GoodsReq
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsResp

@Api(tags = ["机构产品操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/goods")
interface GoodsShareService : BaseShareService<GoodsReq, GoodsResp>