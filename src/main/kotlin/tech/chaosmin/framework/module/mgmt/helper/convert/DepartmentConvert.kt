package tech.chaosmin.framework.module.mgmt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.module.mgmt.entity.DepartmentEntity
import tech.chaosmin.framework.module.mgmt.entity.request.DepartmentReq
import tech.chaosmin.framework.module.mgmt.entity.response.DepartmentResp

@Mapper
interface DepartmentConvert {
    companion object {
        val INSTANCE: DepartmentConvert = Mappers.getMapper(DepartmentConvert::class.java)
    }

    fun convert2Resp(source: DepartmentEntity): DepartmentResp

    fun convert2Resp(source: List<DepartmentEntity>): List<DepartmentResp>

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
    fun convert2Entity(request: DepartmentReq): DepartmentEntity

}