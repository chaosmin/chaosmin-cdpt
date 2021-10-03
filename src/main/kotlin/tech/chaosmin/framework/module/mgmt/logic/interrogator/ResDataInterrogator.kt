/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * ResDataInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.mgmt.logic.interrogator

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ResData
import tech.chaosmin.framework.module.mgmt.entity.ResDataEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.ResDataMapper
import tech.chaosmin.framework.module.mgmt.service.ResDataService

/**
 * @author Romani min
 * @since 2021/9/4 23:49
 */
@Component
class ResDataInterrogator(private val resDataService: ResDataService) : Interrogator<ResDataEntity, ResData> {
    override fun getOne(id: Long): ResDataEntity? {
        val resData = resDataService.getById(id)
        return ResDataMapper.INSTANCE.convert2Entity(resData)
    }

    override fun page(cond: PageQuery<ResData>): IPage<ResDataEntity> {
        val page = resDataService.page(cond.page, cond.wrapper)
        return page.convert(ResDataMapper.INSTANCE::convert2Entity)
    }
}