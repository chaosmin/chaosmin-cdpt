package tech.chaosmin.framework.handler.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.domain.entity.DepartmentEntity
import tech.chaosmin.framework.domain.request.DepartmentReq
import tech.chaosmin.framework.domain.response.DepartmentResp
import tech.chaosmin.framework.handler.convert.base.BaseConvert

@Mapper
interface DepartmentConvert : BaseConvert<DepartmentEntity, DepartmentReq, DepartmentResp> {
    companion object {
        val INSTANCE: DepartmentConvert = Mappers.getMapper(DepartmentConvert::class.java)
    }
}