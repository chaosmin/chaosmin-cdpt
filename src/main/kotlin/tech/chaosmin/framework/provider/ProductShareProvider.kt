package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.dataobject.Product
import tech.chaosmin.framework.dao.dataobject.ext.ProductExt
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.entity.ProductEntity
import tech.chaosmin.framework.domain.request.ProductReq
import tech.chaosmin.framework.domain.response.ProductResp
import tech.chaosmin.framework.handler.ModifyProductHandler
import tech.chaosmin.framework.handler.convert.ProductConvert
import tech.chaosmin.framework.handler.logic.ProductQueryLogic
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.ProductShareService
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class ProductShareProvider(
    private val productQueryLogic: ProductQueryLogic,
    private val modifyProductHandler: ModifyProductHandler
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