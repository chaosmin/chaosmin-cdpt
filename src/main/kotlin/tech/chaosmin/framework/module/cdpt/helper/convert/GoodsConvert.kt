package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.cdpt.entity.GoodsEntity
import tech.chaosmin.framework.module.cdpt.entity.request.GoodsReq
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsResp

/**
 * @author Romani min
 * @since 2020/12/23 21:30
 */
@Mapper
interface GoodsConvert : BaseConvert<GoodsEntity, GoodsReq, GoodsResp> {
    companion object {
        val INSTANCE: GoodsConvert = Mappers.getMapper(GoodsConvert::class.java)
    }
}