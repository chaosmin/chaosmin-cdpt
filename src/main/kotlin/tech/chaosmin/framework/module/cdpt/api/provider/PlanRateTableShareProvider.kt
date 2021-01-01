package tech.chaosmin.framework.module.cdpt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.api.PlanRateTableShareService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanRateTable
import tech.chaosmin.framework.module.cdpt.entity.PlanRateTableEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PlanRateTableReq
import tech.chaosmin.framework.module.cdpt.entity.response.PlanRateTableResp
import tech.chaosmin.framework.module.cdpt.handler.ModifyPlanRateTableHandler
import tech.chaosmin.framework.module.cdpt.handler.logic.PlanRateTableQueryLogic
import tech.chaosmin.framework.module.cdpt.helper.convert.ProductPlanRateTableConvert
import tech.chaosmin.framework.utils.RequestUtil
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class PlanRateTableShareProvider(
    private val planRateTableQueryLogic: PlanRateTableQueryLogic,
    private val modifyPlanRateTableHandler: ModifyPlanRateTableHandler
) : PlanRateTableShareService {
    override fun selectById(id: Long): RestResult<PlanRateTableResp?> {
        val productPlanRateTable = planRateTableQueryLogic.get(id)
        return if (productPlanRateTable == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(ProductPlanRateTableConvert.INSTANCE.convert2Resp(productPlanRateTable))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<PlanRateTableResp>> {
        val queryCondition = RequestUtil.getQueryCondition<PlanRateTable>(request)
        val page = planRateTableQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(ProductPlanRateTableConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: PlanRateTableReq): RestResult<PlanRateTableResp> {
        val productPlanRateTable = ProductPlanRateTableConvert.INSTANCE.convert2Entity(req)
        productPlanRateTable.save()
        return RestResultExt.execute(modifyPlanRateTableHandler, productPlanRateTable, ProductPlanRateTableConvert::class.java)
    }

    override fun update(id: Long, req: PlanRateTableReq): RestResult<PlanRateTableResp> {
        val productPlanRateTable = ProductPlanRateTableConvert.INSTANCE.convert2Entity(req)
        productPlanRateTable.update(id)
        return RestResultExt.execute(modifyPlanRateTableHandler, productPlanRateTable, ProductPlanRateTableConvert::class.java)
    }

    override fun delete(id: Long): RestResult<PlanRateTableResp> {
        val productPlanRateTable = PlanRateTableEntity(id)
        productPlanRateTable.remove()
        return RestResultExt.execute(modifyPlanRateTableHandler, productPlanRateTable, ProductPlanRateTableConvert::class.java)
    }
}