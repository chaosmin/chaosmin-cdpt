package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.dataobject.Department
import tech.chaosmin.framework.domain.request.share.DepartmentShareRequestDTO
import tech.chaosmin.framework.domain.response.share.DepartmentShareResponseDTO

@Mapper
interface DepartmentConvert {
    companion object {
        val INSTANCE: DepartmentConvert = Mappers.getMapper(DepartmentConvert::class.java)
    }

    fun convertToShareResponse(department: Department): DepartmentShareResponseDTO

    fun convertToShareResponse(departments: List<Department>): List<DepartmentShareResponseDTO>

    fun convertToBaseBean(requestDTO: DepartmentShareRequestDTO): Department
}