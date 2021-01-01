package tech.chaosmin.framework.module.cdpt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.api.PlanLiabilityShareService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanLiability
import tech.chaosmin.framework.module.cdpt.entity.PlanLiabilityEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PlanLiabilityReq
import tech.chaosmin.framework.module.cdpt.entity.response.PlanLiabilityResp
import tech.chaosmin.framework.module.cdpt.handler.ModifyPlanLiabilityHandler
import tech.chaosmin.framework.module.cdpt.handler.logic.PlanLiabilityQueryLogic
import tech.chaosmin.framework.module.cdpt.helper.convert.ProductPlanLiabilityConvert
import tech.chaosmin.framework.utils.RequestUtil
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class PlanLiabilityShareProvider(
    private val planLiabilityQueryLogic: PlanLiabilityQueryLogic,
    private val modifyPlanLiabilityHandler: ModifyPlanLiabilityHandler
) : PlanLiabilityShareService {
    override fun selectById(id: Long): RestResult<PlanLiabilityResp?> {
        val productPlanLiability = planLiabilityQueryLogic.get(id)
        return if (productPlanLiability == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(ProductPlanLiabilityConvert.INSTANCE.convert2Resp(productPlanLiability))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<PlanLiabilityResp>> {
        val queryCondition = RequestUtil.getQueryCondition<PlanLiability>(request)
        val page = planLiabilityQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(ProductPlanLiabilityConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: PlanLiabilityReq): RestResult<PlanLiabilityResp> {
        val productPlanLiability = ProductPlanLiabilityConvert.INSTANCE.convert2Entity(req)
        productPlanLiability.save()
        return RestResultExt.execute(modifyPlanLiabilityHandler, productPlanLiability, ProductPlanLiabilityConvert::class.java)
    }

    override fun update(id: Long, req: PlanLiabilityReq): RestResult<PlanLiabilityResp> {
        val productPlanLiability = ProductPlanLiabilityConvert.INSTANCE.convert2Entity(req)
        productPlanLiability.update(id)
        return RestResultExt.execute(modifyPlanLiabilityHandler, productPlanLiability, ProductPlanLiabilityConvert::class.java)
    }

    override fun delete(id: Long): RestResult<PlanLiabilityResp> {
        val productPlanLiability = PlanLiabilityEntity(id)
        productPlanLiability.remove()
        return RestResultExt.execute(modifyPlanLiabilityHandler, productPlanLiability, ProductPlanLiabilityConvert::class.java)
    }
}