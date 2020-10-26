package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.dataobject.Goods
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.convert.GoodsConvert
import tech.chaosmin.framework.domain.request.share.GoodsShareRequestDTO
import tech.chaosmin.framework.domain.response.share.GoodsShareResponseDTO
import tech.chaosmin.framework.service.GoodsService
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.GoodsShareService
import javax.servlet.http.HttpServletRequest

@RestController
open class GoodsShareProvider(
    private val goodsService: GoodsService
) : GoodsShareService {
    override fun selectById(id: Long): RestResult<GoodsShareResponseDTO?> {
        val goods = goodsService.getById(id)
        return if (goods != null) {
            val response = GoodsConvert.INSTANCE.convertToShareResponse(goods)
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.successRestResult()
        }
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<GoodsShareResponseDTO>> {
        val queryCondition = RequestUtil.getQueryCondition<Goods>(request)
        val page = goodsService.page(queryCondition.page, queryCondition.wrapper)
        return RestResultExt.successRestResult(page.convert(GoodsConvert.INSTANCE::convertToShareResponse))
    }

    @Transactional
    override fun save(requestDTO: GoodsShareRequestDTO): RestResult<GoodsShareResponseDTO> {
        val goods = GoodsConvert.INSTANCE.convertToBaseBean(requestDTO)
        return if (goodsService.save(goods)) {
            val response = GoodsConvert.INSTANCE.convertToShareResponse(goods)
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun update(id: Long, requestDTO: GoodsShareRequestDTO): RestResult<GoodsShareResponseDTO> {
        val goods = GoodsConvert.INSTANCE.convertToBaseBean(requestDTO).apply { this.id = id }
        return if (goodsService.updateById(goods)) {
            val response = GoodsConvert.INSTANCE.convertToShareResponse(goodsService.getById(goods.id))
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun delete(id: Long): RestResult<GoodsShareResponseDTO> {
        return if (goodsService.removeById(id)) {
            RestResultExt.successRestResult()
        } else {
            RestResultExt.failureRestResult()
        }
    }
}