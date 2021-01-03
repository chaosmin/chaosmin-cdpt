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
import tech.chaosmin.framework.module.cdpt.helper.convert.PlanLiabilityConvert
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
        val planLiability = planLiabilityQueryLogic.get(id)
        return if (planLiability == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(PlanLiabilityConvert.INSTANCE.convert2Resp(planLiability))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<PlanLiabilityResp?>> {
        val queryCondition = RequestUtil.getQueryCondition<PlanLiability>(request)
        val page = planLiabilityQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(PlanLiabilityConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: PlanLiabilityReq): RestResult<PlanLiabilityResp> {
        val planLiability = PlanLiabilityConvert.INSTANCE.convert2Entity(req)
        planLiability.save()
        return RestResultExt.execute(modifyPlanLiabilityHandler, planLiability, PlanLiabilityConvert::class.java)
    }

    override fun update(id: Long, req: PlanLiabilityReq): RestResult<PlanLiabilityResp> {
        val planLiability = PlanLiabilityConvert.INSTANCE.convert2Entity(req)
        planLiability.update(id)
        return RestResultExt.execute(modifyPlanLiabilityHandler, planLiability, PlanLiabilityConvert::class.java)
    }

    override fun delete(id: Long): RestResult<PlanLiabilityResp> {
        val planLiability = PlanLiabilityEntity(id)
        planLiability.remove()
        return RestResultExt.execute(modifyPlanLiabilityHandler, planLiability, PlanLiabilityConvert::class.java)
    }
}