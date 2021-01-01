package tech.chaosmin.framework.module.mgmt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.mgmt.entity.DepartmentEntity
import tech.chaosmin.framework.module.mgmt.entity.request.DepartmentReq
import tech.chaosmin.framework.module.mgmt.entity.response.DepartmentResp

@Mapper
interface DepartmentConvert : BaseConvert<DepartmentEntity, DepartmentReq, DepartmentResp> {
    companion object {
        val INSTANCE: DepartmentConvert = Mappers.getMapper(DepartmentConvert::class.java)
    }
}