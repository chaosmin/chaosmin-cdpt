package tech.chaosmin.framework.module.mgmt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.module.mgmt.entity.LetterHeadEntity
import tech.chaosmin.framework.module.mgmt.entity.request.LetterHeadReq
import tech.chaosmin.framework.module.mgmt.entity.response.LetterHeadResp

@Mapper
interface LetterHeadConvert {
    companion object {
        val INSTANCE: LetterHeadConvert = Mappers.getMapper(LetterHeadConvert::class.java)
    }

    fun convert2Resp(source: LetterHeadEntity): LetterHeadResp

    fun convert2Resp(source: List<LetterHeadEntity>): List<LetterHeadResp>

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
    fun convert2Entity(request: LetterHeadReq): LetterHeadEntity

    fun convert2Entity(request: List<LetterHeadReq>): List<LetterHeadEntity>

}