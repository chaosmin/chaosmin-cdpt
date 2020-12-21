package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.dataobject.Department
import tech.chaosmin.framework.domain.request.DepartmentReq
import tech.chaosmin.framework.domain.response.DepartmentResp

@Mapper
interface DepartmentConvert {
    companion object {
        val INSTANCE: DepartmentConvert = Mappers.getMapper(DepartmentConvert::class.java)
    }

    fun convert2Resp(department: Department): DepartmentResp

    fun convert2Resp(departments: List<Department>): List<DepartmentResp>

    fun convert2Entity(req: DepartmentReq): Department
}