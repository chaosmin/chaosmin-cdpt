package tech.chaosmin.framework.module.cdpt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Partner
import tech.chaosmin.framework.module.cdpt.entity.PartnerEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.PartnerMapper
import tech.chaosmin.framework.module.cdpt.service.inner.PartnerService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class PartnerQueryLogic(private val partnerService: PartnerService) : BaseQueryLogic<PartnerEntity, Partner> {

    override fun get(id: Long): PartnerEntity? {
        val partner = partnerService.getById(id)
        return PartnerMapper.INSTANCE.convert2Entity(partner)
    }

    override fun page(cond: PageQuery<Partner>): IPage<PartnerEntity?> {
        val page = partnerService.page(cond.page, cond.wrapper)
        return page.convert(PartnerMapper.INSTANCE::convert2Entity)
    }
}