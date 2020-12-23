package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.dataobject.ProductCategory
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.entity.ProductCategoryEntity
import tech.chaosmin.framework.domain.request.ProductCategoryReq
import tech.chaosmin.framework.domain.response.ProductCategoryResp
import tech.chaosmin.framework.handler.ModifyProductCategoryHandler
import tech.chaosmin.framework.handler.convert.ProductCategoryConvert
import tech.chaosmin.framework.handler.logic.ProductCategoryQueryLogic
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.ProductCategoryShareService
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class ProductCategoryShareProvider(
    private val productCategoryQueryLogic: ProductCategoryQueryLogic,
    private val modifyProductCategoryHandler: ModifyProductCategoryHandler
) : ProductCategoryShareService {
    override fun selectById(id: Long): RestResult<ProductCategoryResp?> {
        val productCategory = productCategoryQueryLogic.get(id)
        return if (productCategory == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(ProductCategoryConvert.INSTANCE.convert2Resp(productCategory))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<ProductCategoryResp>> {
        val queryCondition = RequestUtil.getQueryCondition<ProductCategory>(request)
        val page = productCategoryQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(ProductCategoryConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: ProductCategoryReq): RestResult<ProductCategoryResp> {
        val productCategory = ProductCategoryConvert.INSTANCE.convert2Entity(req)
        productCategory.save()
        return RestResultExt.execute(modifyProductCategoryHandler, productCategory, ProductCategoryConvert::class.java)
    }

    override fun update(id: Long, req: ProductCategoryReq): RestResult<ProductCategoryResp> {
        val productCategory = ProductCategoryConvert.INSTANCE.convert2Entity(req)
        productCategory.update(id)
        return RestResultExt.execute(modifyProductCategoryHandler, productCategory, ProductCategoryConvert::class.java)
    }

    override fun delete(id: Long): RestResult<ProductCategoryResp> {
        val productCategory = ProductCategoryEntity(id)
        productCategory.remove()
        return RestResultExt.execute(modifyProductCategoryHandler, productCategory, ProductCategoryConvert::class.java)
    }
}