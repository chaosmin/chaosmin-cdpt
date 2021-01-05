package tech.chaosmin.framework.module.cdpt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
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
import tech.chaosmin.framework.module.cdpt.service.GoodsPlanService
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.utils.SecurityUtil
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class ProductPlanShareProvider(
    private val goodsPlanService: GoodsPlanService,
    private val productPlanQueryLogic: ProductPlanQueryLogic,
    private val modifyProductPlanHandler: ModifyProductPlanHandler
) : ProductPlanShareService {
    override fun selectById(id: Long): RestResult<ProductPlanResp?> {
        val productPlan = productPlanQueryLogic.get(id)
        return if (productPlan == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(ProductPlanConvert.INSTANCE.convert2Resp(productPlan))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<ProductPlanResp?>> {
        val queryCondition = RequestUtil.getQueryCondition<ProductPlanExt>(request)
        // 如果是非管理员用户仅能查看自己被授权的产品计划信息
        if (SecurityUtil.getUserDetails()?.isAdmin != true) {
            val planIds = goodsPlanService.getEqUser(SecurityUtil.getUserId()).mapNotNull { it.productPlanId }
            queryCondition.wrapper.`in`("product_plan.id", planIds)
        }
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