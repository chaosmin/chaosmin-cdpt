package tech.chaosmin.framework.module.mgmt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Department
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.DepartmentExt
import tech.chaosmin.framework.module.mgmt.entity.DepartmentEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:13
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface DepartmentMapper {
    companion object {
        val INSTANCE: DepartmentMapper = Mappers.getMapper(DepartmentMapper::class.java)
    }

    fun convert2DO(source: DepartmentEntity?): Department?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: Department?): DepartmentEntity?

    fun convert2Entity(source: DepartmentExt?): DepartmentEntity?

    fun convert2Entity(source: List<Department>?): List<DepartmentEntity>?
}