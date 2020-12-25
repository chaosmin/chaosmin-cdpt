package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.convert.base.BaseMapper
import tech.chaosmin.framework.dao.convert.base.KeyValueEnumMapper
import tech.chaosmin.framework.dao.dataobject.Department
import tech.chaosmin.framework.dao.dataobject.ext.DepartmentExt
import tech.chaosmin.framework.domain.entity.DepartmentEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:13
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface DepartmentMapper : BaseMapper<DepartmentEntity, Department> {
    companion object {
        val INSTANCE: DepartmentMapper = Mappers.getMapper(DepartmentMapper::class.java)
    }

    fun convert2Entity(source: DepartmentExt): DepartmentEntity
}