package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.dataobject.ProductPlanRateTable
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.entity.ProductPlanRateTableEntity
import tech.chaosmin.framework.domain.request.ProductPlanRateTableReq
import tech.chaosmin.framework.domain.response.ProductPlanRateTableResp
import tech.chaosmin.framework.handler.ModifyProductPlanRateTableHandler
import tech.chaosmin.framework.handler.convert.ProductPlanRateTableConvert
import tech.chaosmin.framework.handler.logic.ProductPlanRateTableQueryLogic
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.ProductPlanRateTableShareService
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class ProductPlanRateTableShareProvider(
    private val productPlanRateTableQueryLogic: ProductPlanRateTableQueryLogic,
    private val modifyProductPlanRateTableHandler: ModifyProductPlanRateTableHandler
) : ProductPlanRateTableShareService {
    override fun selectById(id: Long): RestResult<ProductPlanRateTableResp?> {
        val productPlanRateTable = productPlanRateTableQueryLogic.get(id)
        return if (productPlanRateTable == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(ProductPlanRateTableConvert.INSTANCE.convert2Resp(productPlanRateTable))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<ProductPlanRateTableResp>> {
        val queryCondition = RequestUtil.getQueryCondition<ProductPlanRateTable>(request)
        val page = productPlanRateTableQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(ProductPlanRateTableConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: ProductPlanRateTableReq): RestResult<ProductPlanRateTableResp> {
        val productPlanRateTable = ProductPlanRateTableConvert.INSTANCE.convert2Entity(req)
        productPlanRateTable.save()
        return RestResultExt.execute(modifyProductPlanRateTableHandler, productPlanRateTable, ProductPlanRateTableConvert::class.java)
    }

    override fun update(id: Long, req: ProductPlanRateTableReq): RestResult<ProductPlanRateTableResp> {
        val productPlanRateTable = ProductPlanRateTableConvert.INSTANCE.convert2Entity(req)
        productPlanRateTable.update(id)
        return RestResultExt.execute(modifyProductPlanRateTableHandler, productPlanRateTable, ProductPlanRateTableConvert::class.java)
    }

    override fun delete(id: Long): RestResult<ProductPlanRateTableResp> {
        val productPlanRateTable = ProductPlanRateTableEntity(id)
        productPlanRateTable.remove()
        return RestResultExt.execute(modifyProductPlanRateTableHandler, productPlanRateTable, ProductPlanRateTableConvert::class.java)
    }
}