package tech.chaosmin.framework.module.cdpt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.api.ProductShareService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductExt
import tech.chaosmin.framework.module.cdpt.entity.ProductEntity
import tech.chaosmin.framework.module.cdpt.entity.request.ProductReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductResp
import tech.chaosmin.framework.module.cdpt.handler.ModifyProductHandler
import tech.chaosmin.framework.module.cdpt.handler.UploadProductHandler
import tech.chaosmin.framework.module.cdpt.handler.logic.ProductQueryLogic
import tech.chaosmin.framework.module.cdpt.helper.convert.ProductConvert
import tech.chaosmin.framework.module.mgmt.entity.request.UploadFileReq
import tech.chaosmin.framework.utils.RequestUtil
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class ProductShareProvider(
    private val productQueryLogic: ProductQueryLogic,
    private val modifyProductHandler: ModifyProductHandler,
    private val uploadProductHandler: UploadProductHandler
) : ProductShareService {
    override fun selectById(id: Long): RestResult<ProductResp?> {
        val product = productQueryLogic.get(id)
        return if (product == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(ProductConvert.INSTANCE.convert2Resp(product))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<ProductResp>> {
        val queryCondition = RequestUtil.getQueryCondition<ProductExt>(request)
        val page = productQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(ProductConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: ProductReq): RestResult<ProductResp> {
        val product = ProductConvert.INSTANCE.convert2Entity(req)
        product.save()
        return RestResultExt.execute(modifyProductHandler, product, ProductConvert::class.java)
    }

    override fun upload(req: UploadFileReq): RestResult<ProductResp> {
        // TODO("OSS保存原始文件")
        req.fileName = req.file?.originalFilename
        val result = uploadProductHandler.operate(req)
        return if (result.success && result.data != null) {
            RestResultExt.successRestResult(ProductConvert.INSTANCE.convert2Resp(result.data!!))
        } else {
            RestResultExt.mapper(result)
        }
    }

    override fun update(id: Long, req: ProductReq): RestResult<ProductResp> {
        val product = ProductConvert.INSTANCE.convert2Entity(req)
        product.update(id)
        return RestResultExt.execute(modifyProductHandler, product, ProductConvert::class.java)
    }

    override fun delete(id: Long): RestResult<ProductResp> {
        val product = ProductEntity(id)
        product.remove()
        return RestResultExt.execute(modifyProductHandler, product, ProductConvert::class.java)
    }
}