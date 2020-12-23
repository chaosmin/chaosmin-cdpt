package tech.chaosmin.framework.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.dao.convert.PartnerMapper
import tech.chaosmin.framework.dao.dataobject.Partner
import tech.chaosmin.framework.domain.PageQuery
import tech.chaosmin.framework.domain.entity.PartnerEntity
import tech.chaosmin.framework.handler.logic.base.BaseQueryLogic
import tech.chaosmin.framework.service.PartnerService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class PartnerQueryLogic(private val partnerService: PartnerService) : BaseQueryLogic<PartnerEntity, Partner> {

    override fun get(id: Long): PartnerEntity? {
        val partner = partnerService.getById(id)
        return if (partner == null) null
        else PartnerMapper.INSTANCE.convert2Entity(partner)
    }

    override fun page(cond: PageQuery<Partner>): IPage<PartnerEntity> {
        val page = partnerService.page(cond.page, cond.wrapper)
        return page.convert(PartnerMapper.INSTANCE::convert2Entity)
    }
}