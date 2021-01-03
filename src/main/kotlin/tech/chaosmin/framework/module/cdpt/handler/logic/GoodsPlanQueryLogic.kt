package tech.chaosmin.framework.module.cdpt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.GoodsPlanExt
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.GoodsPlanMapper
import tech.chaosmin.framework.module.cdpt.service.GoodsPlanService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class GoodsPlanQueryLogic(private val goodsPlanService: GoodsPlanService) : BaseQueryLogic<GoodsPlanEntity, GoodsPlanExt> {

    override fun get(id: Long): GoodsPlanEntity? {
        val goodsPlan = goodsPlanService.getByIdExt(id)
        return GoodsPlanMapper.INSTANCE.convertEx2Entity(goodsPlan)
    }

    override fun page(cond: PageQuery<GoodsPlanExt>): IPage<GoodsPlanEntity?> {
        val page = goodsPlanService.pageExt(cond.page, cond.wrapper)
        return page.convert(GoodsPlanMapper.INSTANCE::convertEx2Entity)
    }
}