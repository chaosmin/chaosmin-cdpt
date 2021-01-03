package tech.chaosmin.framework.module.cdpt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.api.GoodsPlanShareService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.GoodsPlan
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.GoodsPlanExt
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.entity.request.GoodsPlanReq
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsPlanResp
import tech.chaosmin.framework.module.cdpt.handler.ModifyGoodsPlanHandler
import tech.chaosmin.framework.module.cdpt.handler.logic.GoodsPlanQueryLogic
import tech.chaosmin.framework.module.cdpt.helper.convert.GoodsPlanConvert
import tech.chaosmin.framework.utils.RequestUtil
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class GoodsPlanShareProvider(
    private val goodsPlanQueryLogic: GoodsPlanQueryLogic,
    private val modifyGoodsPlanHandler: ModifyGoodsPlanHandler
) : GoodsPlanShareService {
    override fun selectById(id: Long): RestResult<GoodsPlanResp?> {
        val goodsPlan = goodsPlanQueryLogic.get(id)
        return if (goodsPlan == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(GoodsPlanConvert.INSTANCE.convert2Resp(goodsPlan))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<GoodsPlanResp?>> {
        val queryCondition = RequestUtil.getQueryCondition<GoodsPlanExt>(request)
        val page = goodsPlanQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(GoodsPlanConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: GoodsPlanReq): RestResult<GoodsPlanResp> {
        val goodsPlan = GoodsPlanConvert.INSTANCE.convert2Entity(req)
        goodsPlan.save()
        return RestResultExt.execute(modifyGoodsPlanHandler, goodsPlan, GoodsPlanConvert::class.java)
    }

    override fun update(id: Long, req: GoodsPlanReq): RestResult<GoodsPlanResp> {
        val goodsPlan = GoodsPlanConvert.INSTANCE.convert2Entity(req)
        goodsPlan.update(id)
        return RestResultExt.execute(modifyGoodsPlanHandler, goodsPlan, GoodsPlanConvert::class.java)
    }

    override fun delete(id: Long): RestResult<GoodsPlanResp> {
        val goodsPlan = GoodsPlanEntity(id)
        goodsPlan.remove()
        return RestResultExt.execute(modifyGoodsPlanHandler, goodsPlan, GoodsPlanConvert::class.java)
    }
}