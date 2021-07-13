package tech.chaosmin.framework.module.mgmt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ResData
import tech.chaosmin.framework.module.mgmt.entity.ResDataEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:13
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface ResDataMapper {
    companion object {
        val INSTANCE: ResDataMapper = Mappers.getMapper(ResDataMapper::class.java)
    }

    fun convert2DO(source: ResDataEntity?): ResData?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: ResData?): ResDataEntity?

    fun convert2Entity(source: List<ResData>?): List<ResDataEntity>?
}