/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api.provider

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.AbstractAPI
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.api.ProductAPI
import tech.chaosmin.framework.module.cdpt.api.convert.ProductConvert
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductEx
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.entity.ProductEntity
import tech.chaosmin.framework.module.cdpt.entity.request.ProductReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductResp
import tech.chaosmin.framework.module.cdpt.logic.handler.GoodsPlanModifyHandler
import tech.chaosmin.framework.module.cdpt.logic.handler.ProductModifyHandler
import tech.chaosmin.framework.module.cdpt.logic.handler.UploadProductHandler
import tech.chaosmin.framework.module.cdpt.logic.interrogator.ProductInterrogator
import tech.chaosmin.framework.module.mgmt.entity.request.UploadFileReq
import tech.chaosmin.framework.utils.SecurityUtil
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class ProductProvider(
    private val productInterrogator: ProductInterrogator,
    private val productModifyHandler: ProductModifyHandler,
    private val goodsPlanModifyHandler: GoodsPlanModifyHandler,
    private val uploadProductHandler: UploadProductHandler
) : AbstractAPI<ProductEx, ProductEntity, ProductReq, ProductResp>(
    ProductConvert.INSTANCE, productInterrogator, productModifyHandler
), ProductAPI {
    private val logger = LoggerFactory.getLogger(ProductAPI::class.java)

    override fun upload(req: UploadFileReq): RestResult<ProductResp> {
        // TODO("OSS保存原始文件")
        req.fileName = req.file?.originalFilename
        val result = uploadProductHandler.operate(req)
        return if (result.success && result.data != null) {
            val goodsPlan = GoodsPlanEntity().apply {
                this.userIds = Collections.singletonList(SecurityUtil.getUserId())
                this.plans = result.data?.plans?.associate { it.id!! to it.comsRatio!! }
            }
            logger.info("Assign product plan: ${goodsPlan.plans} to ${goodsPlan.userId}")
            goodsPlanModifyHandler.operate(goodsPlan.save())
            RestResultExt.successRestResult(ProductConvert.INSTANCE.toResp(result.data!!))
        } else {
            RestResultExt.mapper(result)
        }
    }
}