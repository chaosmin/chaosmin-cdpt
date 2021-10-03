/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseAPI
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.cdpt.entity.request.ProductReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductResp
import tech.chaosmin.framework.module.mgmt.entity.request.UploadFileReq

@Api(tags = ["产品操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/products")
interface ProductAPI : BaseAPI<ProductReq, ProductResp> {
    @PostMapping("/file")
    @ApiOperation("上传文件 解析产品信息")
    fun upload(req: UploadFileReq): RestResult<ProductResp>
}