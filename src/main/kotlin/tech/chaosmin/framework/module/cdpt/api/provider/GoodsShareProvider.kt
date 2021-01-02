package tech.chaosmin.framework.module.cdpt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.api.GoodsShareService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Goods
import tech.chaosmin.framework.module.cdpt.entity.GoodsEntity
import tech.chaosmin.framework.module.cdpt.entity.request.GoodsReq
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsResp
import tech.chaosmin.framework.module.cdpt.handler.ModifyGoodsHandler
import tech.chaosmin.framework.module.cdpt.handler.logic.GoodsQueryLogic
import tech.chaosmin.framework.module.cdpt.helper.convert.GoodsConvert
import tech.chaosmin.framework.utils.RequestUtil
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class GoodsShareProvider(
    private val goodsQueryLogic: GoodsQueryLogic,
    private val modifyGoodsHandler: ModifyGoodsHandler
) : GoodsShareService {
    override fun selectById(id: Long): RestResult<GoodsResp?> {
        val goods = goodsQueryLogic.get(id)
        return if (goods == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(GoodsConvert.INSTANCE.convert2Resp(goods))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<GoodsResp>> {
        val queryCondition = RequestUtil.getQueryCondition<Goods>(request)
        val page = goodsQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(GoodsConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: GoodsReq): RestResult<GoodsResp> {
        val goods = GoodsConvert.INSTANCE.convert2Entity(req)
        goods.save()
        return RestResultExt.execute(modifyGoodsHandler, goods, GoodsConvert::class.java)
    }

    override fun update(id: Long, req: GoodsReq): RestResult<GoodsResp> {
        val goods = GoodsConvert.INSTANCE.convert2Entity(req)
        goods.update(id)
        return RestResultExt.execute(modifyGoodsHandler, goods, GoodsConvert::class.java)
    }

    override fun delete(id: Long): RestResult<GoodsResp> {
        val goods = GoodsEntity(id)
        goods.remove()
        return RestResultExt.execute(modifyGoodsHandler, goods, GoodsConvert::class.java)
    }
}