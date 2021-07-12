package tech.chaosmin.framework.module.cdpt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.api.ProductPlanShareService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductPlanExt
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanEntity
import tech.chaosmin.framework.module.cdpt.entity.request.ProductPlanReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductPlanResp
import tech.chaosmin.framework.module.cdpt.handler.ModifyProductPlanHandler
import tech.chaosmin.framework.module.cdpt.handler.logic.ProductPlanQueryLogic
import tech.chaosmin.framework.module.cdpt.helper.convert.ProductPlanConvert
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.utils.SecurityUtil
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
    override fun contractPage(id: Long): RestResult<IPage<ProductPlanResp?>> {
        val page = if (SecurityUtil.getUserDetails().isAdmin) {
            productPlanQueryLogic.page(PageQuery.emptyQuery())
        } else productPlanQueryLogic.contract(id)
        return RestResultExt.successRestResult(page.convert(ProductPlanConvert.INSTANCE::convert2Resp))
    }

    override fun selectById(id: Long): RestResult<ProductPlanResp> {
        val productPlan = productPlanQueryLogic.get(id)
        return if (productPlan == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(ProductPlanConvert.INSTANCE.convert2Resp(productPlan))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<ProductPlanResp>> {
        val queryCondition = RequestUtil.getQueryCondition<ProductPlanExt>(request)
        val page = productPlanQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(ProductPlanConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: ProductPlanReq): RestResult<ProductPlanResp> {
        val productPlan = ProductPlanConvert.INSTANCE.convert2Entity(req).save()
        val result = modifyProductPlanHandler.operate(productPlan)
        return RestResultExt.mapper<ProductPlanResp>(result).convert {
            ProductPlanConvert.INSTANCE.convert2Resp(result.data ?: ProductPlanEntity())
        }
    }

    override fun update(id: Long, req: ProductPlanReq): RestResult<ProductPlanResp> {
        val productPlan = ProductPlanConvert.INSTANCE.convert2Entity(req).update(id)
        val result = modifyProductPlanHandler.operate(productPlan)
        return RestResultExt.mapper<ProductPlanResp>(result).convert {
            ProductPlanConvert.INSTANCE.convert2Resp(result.data ?: ProductPlanEntity())
        }
    }

    override fun delete(id: Long): RestResult<ProductPlanResp> {
        val productPlan = ProductPlanEntity(id).remove()
        val result = modifyProductPlanHandler.operate(productPlan)
        return RestResultExt.mapper<ProductPlanResp>(result).convert {
            ProductPlanConvert.INSTANCE.convert2Resp(result.data ?: ProductPlanEntity())
        }
    }
}