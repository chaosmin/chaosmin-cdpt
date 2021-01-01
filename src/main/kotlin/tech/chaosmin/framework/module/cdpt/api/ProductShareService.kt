package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseShareService
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.cdpt.entity.request.ProductReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductResp
import tech.chaosmin.framework.module.mgmt.entity.request.UploadFileReq

@Api(tags = ["产品操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/products")
interface ProductShareService : BaseShareService<ProductReq, ProductResp> {
    @PostMapping("/file")
    @ApiOperation("上传添加")
    fun upload(req: UploadFileReq): RestResult<ProductResp>
}