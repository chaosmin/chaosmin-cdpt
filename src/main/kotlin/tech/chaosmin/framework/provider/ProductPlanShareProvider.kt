package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.dataobject.ProductPlan
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.entity.ProductPlanEntity
import tech.chaosmin.framework.domain.request.ProductPlanReq
import tech.chaosmin.framework.domain.response.ProductPlanResp
import tech.chaosmin.framework.handler.ModifyProductPlanHandler
import tech.chaosmin.framework.handler.convert.ProductPlanConvert
import tech.chaosmin.framework.handler.logic.ProductPlanQueryLogic
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.ProductPlanShareService
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class ProductPlanShareProvider(
    private val productPlanQueryLogic: ProductPlanQueryLogic,
    private val modifyProductPlanHandler: ModifyProductPlanHandler
) : ProductPlanShareService {
    override fun selectById(id: Long): RestResult<ProductPlanResp?> {
        val productPlan = productPlanQueryLogic.get(id)
        return if (productPlan == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(ProductPlanConvert.INSTANCE.convert2Resp(productPlan))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<ProductPlanResp>> {
        val queryCondition = RequestUtil.getQueryCondition<ProductPlan>(request)
        val page = productPlanQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(ProductPlanConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: ProductPlanReq): RestResult<ProductPlanResp> {
        val productPlan = ProductPlanConvert.INSTANCE.convert2Entity(req)
        productPlan.save()
        return RestResultExt.execute(modifyProductPlanHandler, productPlan, ProductPlanConvert::class.java)
    }

    override fun update(id: Long, req: ProductPlanReq): RestResult<ProductPlanResp> {
        val productPlan = ProductPlanConvert.INSTANCE.convert2Entity(req)
        productPlan.update(id)
        return RestResultExt.execute(modifyProductPlanHandler, productPlan, ProductPlanConvert::class.java)
    }

    override fun delete(id: Long): RestResult<ProductPlanResp> {
        val productPlan = ProductPlanEntity(id)
        productPlan.remove()
        return RestResultExt.execute(modifyProductPlanHandler, productPlan, ProductPlanConvert::class.java)
    }
}