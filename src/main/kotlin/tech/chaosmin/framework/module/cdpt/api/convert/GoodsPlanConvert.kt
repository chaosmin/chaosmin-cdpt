/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.IConvert
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.entity.request.GoodsPlanReq
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsPlanResp

/**
 * @author Romani min
 * @since 2020/12/23 21:30
 */
@Mapper
interface GoodsPlanConvert : IConvert<GoodsPlanEntity, GoodsPlanReq, GoodsPlanResp> {
    companion object {
        val INSTANCE: GoodsPlanConvert = Mappers.getMapper(GoodsPlanConvert::class.java)
    }
}