package tech.chaosmin.framework.module.cdpt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.api.ProductCategoryShareService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductCategory
import tech.chaosmin.framework.module.cdpt.entity.ProductCategoryEntity
import tech.chaosmin.framework.module.cdpt.entity.request.ProductCategoryReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductCategoryResp
import tech.chaosmin.framework.module.cdpt.handler.ModifyProductCategoryHandler
import tech.chaosmin.framework.module.cdpt.handler.logic.ProductCategoryQueryLogic
import tech.chaosmin.framework.module.cdpt.helper.convert.ProductCategoryConvert
import tech.chaosmin.framework.utils.RequestUtil
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