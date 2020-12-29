package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.dataobject.ProductPlanLiability
import tech.chaosmin.framework.dao.dataobject.ext.ProductPlanLiabilityExt
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.entity.ProductPlanLiabilityEntity
import tech.chaosmin.framework.domain.request.ProductPlanLiabilityReq
import tech.chaosmin.framework.domain.response.ProductPlanLiabilityResp
import tech.chaosmin.framework.handler.ModifyProductPlanLiabilityHandler
import tech.chaosmin.framework.handler.convert.ProductPlanLiabilityConvert
import tech.chaosmin.framework.handler.logic.ProductPlanLiabilityQueryLogic
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.ProductPlanLiabilityShareService
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class ProductPlanLiabilityShareProvider(
    private val productPlanLiabilityQueryLogic: ProductPlanLiabilityQueryLogic,
    private val modifyProductPlanLiabilityHandler: ModifyProductPlanLiabilityHandler
) : ProductPlanLiabilityShareService {
    override fun selectById(id: Long): RestResult<ProductPlanLiabilityResp?> {
        val productPlanLiability = productPlanLiabilityQueryLogic.get(id)
        return if (productPlanLiability == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(ProductPlanLiabilityConvert.INSTANCE.convert2Resp(productPlanLiability))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<ProductPlanLiabilityResp>> {
        val queryCondition = RequestUtil.getQueryCondition<ProductPlanLiabilityExt>(request)
        val page = productPlanLiabilityQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(ProductPlanLiabilityConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: ProductPlanLiabilityReq): RestResult<ProductPlanLiabilityResp> {
        val productPlanLiability = ProductPlanLiabilityConvert.INSTANCE.convert2Entity(req)
        productPlanLiability.save()
        return RestResultExt.execute(modifyProductPlanLiabilityHandler, productPlanLiability, ProductPlanLiabilityConvert::class.java)
    }

    override fun update(id: Long, req: ProductPlanLiabilityReq): RestResult<ProductPlanLiabilityResp> {
        val productPlanLiability = ProductPlanLiabilityConvert.INSTANCE.convert2Entity(req)
        productPlanLiability.update(id)
        return RestResultExt.execute(modifyProductPlanLiabilityHandler, productPlanLiability, ProductPlanLiabilityConvert::class.java)
    }

    override fun delete(id: Long): RestResult<ProductPlanLiabilityResp> {
        val productPlanLiability = ProductPlanLiabilityEntity(id)
        productPlanLiability.remove()
        return RestResultExt.execute(modifyProductPlanLiabilityHandler, productPlanLiability, ProductPlanLiabilityConvert::class.java)
    }
}