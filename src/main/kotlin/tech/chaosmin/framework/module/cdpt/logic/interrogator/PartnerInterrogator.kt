/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PartnerInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.interrogator

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Partner
import tech.chaosmin.framework.module.cdpt.domain.service.PartnerService
import tech.chaosmin.framework.module.cdpt.entity.PartnerEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.PartnerMapper

/**
 * @author Romani min
 * @since 2021/9/4 22:23
 */
@Component
class PartnerInterrogator(private val partnerService: PartnerService) : Interrogator<PartnerEntity, Partner> {
    override fun getOne(id: Long): PartnerEntity? {
        val partner = partnerService.getById(id)
        return PartnerMapper.INSTANCE.toEn(partner)
    }

    override fun page(cond: PageQuery<Partner>): IPage<PartnerEntity> {
        val page = partnerService.page(cond.page, cond.wrapper)
        return page.convert(PartnerMapper.INSTANCE::toEn)
    }
}