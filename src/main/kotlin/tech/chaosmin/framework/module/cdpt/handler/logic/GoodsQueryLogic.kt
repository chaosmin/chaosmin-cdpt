package tech.chaosmin.framework.module.cdpt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Goods
import tech.chaosmin.framework.module.cdpt.entity.GoodsEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.GoodsMapper
import tech.chaosmin.framework.module.cdpt.service.GoodsService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class GoodsQueryLogic(private val goodsService: GoodsService) : BaseQueryLogic<GoodsEntity, Goods> {

    override fun get(id: Long): GoodsEntity? {
        val goods = goodsService.getById(id)
        return if (goods == null) null
        else GoodsMapper.INSTANCE.convert2Entity(goods)
    }

    override fun page(cond: PageQuery<Goods>): IPage<GoodsEntity> {
        val page = goodsService.page(cond.page, cond.wrapper)
        return page.convert(GoodsMapper.INSTANCE::convert2Entity)
    }
}