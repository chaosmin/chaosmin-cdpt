package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.entity.request.GoodsPlanReq
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsPlanResp

/**
 * @author Romani min
 * @since 2020/12/23 21:30
 */
@Mapper
interface GoodsPlanConvert : BaseConvert<GoodsPlanEntity, GoodsPlanReq, GoodsPlanResp> {
    companion object {
        val INSTANCE: GoodsPlanConvert = Mappers.getMapper(GoodsPlanConvert::class.java)
    }
}