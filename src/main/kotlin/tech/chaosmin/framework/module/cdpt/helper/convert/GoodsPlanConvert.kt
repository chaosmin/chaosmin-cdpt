package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.entity.request.GoodsPlanReq
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsPlanResp

/**
 * @author Romani min
 * @since 2020/12/23 21:30
 */
@Mapper
interface GoodsPlanConvert {
    companion object {
        val INSTANCE: GoodsPlanConvert = Mappers.getMapper(GoodsPlanConvert::class.java)
    }

    fun convert2Resp(source: GoodsPlanEntity): GoodsPlanResp

    fun convert2Resp(source: List<GoodsPlanEntity>): List<GoodsPlanResp>

    @Mappings(
        value = [
            Mapping(target = "id", ignore = true),
            Mapping(target = "modifyType", ignore = true),
            Mapping(target = "createTime", ignore = true),
            Mapping(target = "creator", ignore = true),
            Mapping(target = "updateTime", ignore = true),
            Mapping(target = "updater", ignore = true),
            Mapping(target = "deleted", ignore = true)
        ]
    )
    fun convert2Entity(request: GoodsPlanReq): GoodsPlanEntity
}