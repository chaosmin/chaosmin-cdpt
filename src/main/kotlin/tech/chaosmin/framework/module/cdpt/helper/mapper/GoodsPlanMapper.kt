package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseMapper
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.GoodsPlan
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.GoodsPlanExt
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:13
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface GoodsPlanMapper : BaseMapper<GoodsPlanEntity, GoodsPlan> {
    companion object {
        val INSTANCE: GoodsPlanMapper = Mappers.getMapper(GoodsPlanMapper::class.java)
    }

    @Mapping(target = "saleDateScope", expression = "java(java.util.Arrays.asList(source.getSaleStartTime(),source.getSaleEndTime()))")
    fun convertEx2Entity(source: GoodsPlanExt?): GoodsPlanEntity?
}